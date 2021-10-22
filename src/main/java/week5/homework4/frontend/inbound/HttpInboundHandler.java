package week5.homework4.frontend.inbound;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import week5.homework4.aop.IHttpOutboundHandler;


import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Component
@ChannelHandler.Sharable
public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    @Autowired
    private IHttpOutboundHandler handler;


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("==== channelReadComplete(ChannelHandlerContext ctx)");
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        System.out.println("==== channelRead(ChannelHandlerContext ctx, Object msg) ");

        if (false == (msg instanceof FullHttpRequest)) {
            return;
        }
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        try {
            //filter.filter(fullRequest, ctx);
            handler.handle(fullRequest, ctx);

        } catch (Exception e) {
            //e.printStackTrace();

            System.err.println(e.getMessage());
            if (fullRequest != null) {
                FullHttpResponse response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);

                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

//    private void handlerTest(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
//        FullHttpResponse response = null;
//        try {
//            String value = "hello,kimmking";
//            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
//            response.headers().set("Content-Type", "application/json");
//            response.headers().setInt("Content-Length", response.content().readableBytes());
//
//        } catch (Exception e) {
//            logger.error("处理测试接口出错", e);
//            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
//        } finally {
//            if (fullRequest != null) {
//                if (!HttpUtil.isKeepAlive(fullRequest)) {
//                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
//                } else {
//                    response.headers().set(CONNECTION, KEEP_ALIVE);
//                    ctx.write(response);
//                }
//            }
//        }
//    }
//
//    @Override
//    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
//        cause.printStackTrace();
//        ctx.close();
//    }

}

package week5.homework4.frontend.httpclient4;


import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;

import lombok.Data;

import week5.homework4.HttpClientTest;
import week5.homework4.aop.IHttpOutboundHandler;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Data
public class HttpOutboundHandler implements IHttpOutboundHandler {
//
//    @Autowired
//    private  HttpEndpointRouter router ;
//    private List<String> backendUrls;


    @Override
    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        //String url = this.backendUrl + fullRequest.uri();
        //路由
        //String url = router.route(this.backendUrls);
        //AOP中把url加在fullRequest
        String url = fullRequest.headers().get(Aop1.URL_NAME);
        FullHttpResponse response = null;
        try {
            //httpclient
            String body = HttpClientTest.getAsString(url);

            byte[] bytesArray = body.getBytes("UTF-8");
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(bytesArray));
            response.headers().set("Content-Type", "application/json");
            response.headers().set("X-proxy-tag", this.getClass().getSimpleName());
            response.headers().setInt("Content-Length", bytesArray.length);
        } catch (Throwable e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            exceptionCaught(ctx, e);
        } finally {
            if (fullRequest != null) {
                if(null == response){
                    response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
                }
                if (!HttpUtil.isKeepAlive(fullRequest)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    //response.headers().set(CONNECTION, KEEP_ALIVE);
                    ctx.write(response);
                }
            }
            ctx.flush();
            //ctx.close();
        }
    }


    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }


}

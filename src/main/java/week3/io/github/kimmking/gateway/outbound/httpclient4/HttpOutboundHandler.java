package week3.io.github.kimmking.gateway.outbound.httpclient4;



import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import week3.io.github.kimmking.gateway.router.HttpEndpointRouter;
import week3.io.github.kimmking.gateway.router.impl.TestHttpEndpointRouter;
import week3.netty.NettyHttpClient;

import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler {

    private final String backendUrl;
    private final HttpEndpointRouter router = new TestHttpEndpointRouter();
    private final List<String> backendUrls;

    public HttpOutboundHandler(String backendUrl) {
        this.backendUrls = null;
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length() - 1) : backendUrl;
    }
    public HttpOutboundHandler(List<String> backendUrls) {
        backendUrls = backendUrls.stream().map(x -> x.endsWith("/") ? x.substring(0, x.length() - 1) : x).collect(Collectors.toList());
        this.backendUrls = backendUrls;
        this.backendUrl = null;
    }


    public void handle(final FullHttpRequest fullRequest, final ChannelHandlerContext ctx) {
        String url = this.backendUrl + fullRequest.uri();
        //路由
        //String url = router.route(this.backendUrls)
        FullHttpResponse response = null;
        try {
            //httpclient
            //String body = HttpClientTest.getAsString(url);

            //nettyclient
            Map<String, String> map = new HashMap<>();
            URL backUrl = new URL(backendUrl);
            String host = backUrl.getHost();
            Integer port = backUrl.getPort();
            NettyHttpClient nettyClient = new NettyHttpClient();
            nettyClient.connect(host, port, map);
            String body = map.get("ret");
            System.out.println("结果是：" + body);

            //todo:为什么这里会报错NPE？？？
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

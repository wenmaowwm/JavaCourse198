package week3.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class NettyHttpClientHandler  extends ChannelInboundHandlerAdapter {
    Map<String, String> map;

    public NettyHttpClientHandler(Map<String, String> map) {
        this.map = map;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx)
            throws Exception {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET
                , new URI("/").toASCIIString());
        request.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain;charset=UTF-8")
                .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());

        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg){
        HttpContent httpContent = (HttpContent) msg;
        ByteBuf buf = httpContent.content();
        String response = buf.toString(StandardCharsets.UTF_8);

        map.put("ret", response);
    }
}

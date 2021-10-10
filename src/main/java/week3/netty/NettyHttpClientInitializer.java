package week3.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;

import java.util.Map;

public class NettyHttpClientInitializer  extends ChannelInitializer<SocketChannel> {
    Map<String, String> map;

    public NettyHttpClientInitializer(Map<String, String> map) {
        this.map = map;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline p = ch.pipeline();
        p.addLast(new HttpClientCodec()).addLast(new HttpObjectAggregator(1024 * 1024));
        p.addLast(new HttpContentDecompressor());
        p.addLast(new NettyHttpClientHandler(map));
    }
}

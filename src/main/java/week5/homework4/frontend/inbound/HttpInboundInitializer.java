package week5.homework4.frontend.inbound;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
	@Value("http://localhost:8808")
	private String proxyServer;

	@Autowired
	private HttpInboundHandler httpInboundHandler;
	
	@Override
	public void initChannel(SocketChannel ch) {
		ChannelPipeline p = ch.pipeline();
//		if (sslCtx != null) {
//			p.addLast(sslCtx.newHandler(ch.alloc()));
//		}
		System.out.println("==== HttpInboundInitializer.initChannel(SocketChannel ch) ");
		p.addLast(new HttpServerCodec());
		//p.addLast(new HttpServerExpectContinueHandler());
		p.addLast(new HttpObjectAggregator(1024 * 1024));
		p.addLast(httpInboundHandler);
	}

	public String getProxyServer() {
		return proxyServer;
	}
}

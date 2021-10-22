package week5.homework4.frontend.httpclient4;

import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import io.netty.util.ReferenceCountUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import week5.homework4.ProxyBizFilter;
import week5.homework4.frontend.router.HttpEndpointRouter;

import java.util.List;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

@Component
@PropertySource("classpath:application.properties")
public class Aop1 {
    public static final String URL_NAME = "backendUrl";
    @Autowired
    private ProxyBizFilter filter;

    //@Value("#{'http://localhost:8808,http://localhost:8808,http://localhost:8808'.split(',')}")
    @Value("#{'${backendUrls}'.split(',')}")
    private List<String> backendUrls;
    @Autowired
    private HttpEndpointRouter router ;

    public void around(ProceedingJoinPoint jp) throws Throwable {
        final FullHttpRequest fullRequest = (FullHttpRequest)jp.getArgs()[0];
        final ChannelHandlerContext ctx = (ChannelHandlerContext)jp.getArgs()[1];
        try{
            filter.filter(fullRequest, ctx);
            String url = router.route(backendUrls);
            fullRequest.headers().add(URL_NAME, url);
        }catch (Exception e){
            System.err.println(e.getMessage());
            throw  e;
        }
        jp.proceed();
    }

    public List<String> getBackendUrls() {
        return backendUrls;
    }

    public void setBackendUrls(List<String> backendUrls) {
        this.backendUrls = backendUrls;
    }
}

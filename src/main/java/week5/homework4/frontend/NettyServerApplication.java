package week5.homework4.frontend;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import week5.homework4.frontend.inbound.HttpInboundServer;

@Component
public class NettyServerApplication {

    @Autowired
    HttpInboundServer server;

    public void start(){

        try {
            server.run();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        NettyServerApplication nettyServerApplication = (NettyServerApplication)context.getBean("nettyServerApplication");
        nettyServerApplication.start();
    }
}

package week5.homework4.frontend.router.impl;

import org.springframework.stereotype.Component;
import week5.homework4.frontend.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

@Component
public class TestHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(size));
    }
}

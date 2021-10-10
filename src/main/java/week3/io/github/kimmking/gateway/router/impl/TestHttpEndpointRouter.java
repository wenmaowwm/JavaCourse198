package week3.io.github.kimmking.gateway.router.impl;

import week3.io.github.kimmking.gateway.router.HttpEndpointRouter;

import java.util.List;
import java.util.Random;

public class TestHttpEndpointRouter implements HttpEndpointRouter {
    @Override
    public String route(List<String> endpoints) {
        int size = endpoints.size();
        Random random = new Random(System.currentTimeMillis());
        return endpoints.get(random.nextInt(size));
    }
}

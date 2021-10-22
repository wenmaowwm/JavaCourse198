package week5.homework1.myProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class AopProxy implements InvocationHandler {
    private final Object object;

    public AopProxy(Object object) {
        this.object = object;
    }

    public Object CreateProxy(){
        return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(),
                this);
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        MyJoinPoint joinPoint = new MyJoinPoint(object, method, args, this);

        return around(joinPoint);
    }

    public void before(){
        System.out.println("begin!!!");
    }
    public void after(){
        System.out.println("end!!!");
    }
    public Object around(MyJoinPoint joinPoint){
        Object ret = null;
        try {
            System.out.println("around begin");
            ret = joinPoint.proceed();
            System.out.println("around end");
        }catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}

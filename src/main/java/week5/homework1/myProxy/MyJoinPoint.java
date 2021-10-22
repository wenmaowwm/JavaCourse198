package week5.homework1.myProxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MyJoinPoint {
    private Object obj;
    private Method method ;
    private Object[] args ;
    private AopProxy proxy;
    public Object proceed() throws InvocationTargetException, IllegalAccessException {
        proxy.before();
        Object ret = method.invoke(obj, args);
        proxy.after();
        return ret;
    }

    public MyJoinPoint(Object obj, Method method, Object[] args, AopProxy proxy) {
        this.obj = obj;
        this.method = method;
        this.args = args;
        this.proxy = proxy;
    }
}

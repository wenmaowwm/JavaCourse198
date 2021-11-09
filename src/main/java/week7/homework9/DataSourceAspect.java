package week7.homework9;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Order(1)
@Component
public class DataSourceAspect {
    // 切点, 注意这里是在service层（具体是配置dao层还是service层需要看自己业务，原则就是当前业务需要在主库执行还是从库执行的判断依据在哪）
    @Pointcut("execution(* week7.homework9.T1Dao.*(..))")
    public void aspect() {
    }

    @Before("aspect()")
    private void before(JoinPoint point) {
        //进入切面
        String method = point.getSignature().getName();//当前切入的方法名

		if (method.startsWith("select") || method.startsWith("get")) { //根据自己业务做判断主库从库切换
            DataSourceRouter.setSlave();//设置为当前使用从库
        }else {
            DataSourceRouter.setMater();
        }
    }
}

package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.MethodLog;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Order(1)
@Component
public class MethodLogAspect {

    @Around("@annotation(methodLog)")
    public Object aroundMethod(ProceedingJoinPoint point, MethodLog methodLog) throws Throwable {

        Object[] args = point.getArgs();
        StringBuilder sb = new StringBuilder();
        // class Name
        sb.append(point.getTarget().getClass().getName());
        sb.append("-");
        sb.append(point.getSignature().getName());

        if(args != null && args.length > 0){
            sb.append("-params[");
            for(Object arg : args){
                sb.append(arg);
                sb.append(",");
            }
            sb.append("]");
        }
        long start = System.currentTimeMillis();
        System.out.println(sb.toString() + " start at: " + start);
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        System.out.println(sb.toString() + " end at: " + end);

        System.out.println(sb.toString() + "cost time: " + (end-start));
        return result;

    }
}


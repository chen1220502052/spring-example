package org.example.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.example.annotation.MethodLog;
import org.example.utils.TimeUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;


@Aspect
@Order(1)
@Component
public class MethodLogAspect {

    private static Logger logger = LoggerFactory.getLogger(MethodLogAspect.class);

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
        logger.info(TimeUtil.getCurrentTimeStr() + "-" + sb.toString() + " method start...");
        Object result = point.proceed();
        long end = System.currentTimeMillis();
        logger.info(TimeUtil.getCurrentTimeStr() + "-" + sb.toString() + "method end ...");

        logger.info(TimeUtil.getCurrentTimeStr() + "-" + sb.toString() + "cost time: " + (end-start));
        return result;

    }
}


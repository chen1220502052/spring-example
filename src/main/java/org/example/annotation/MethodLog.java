package org.example.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodLog {
    String name() default  ""; // 方法名 可以为空
    String startLog() default  ""; // 开始log
    String endLog() default ""; // 结束log
    String params() default ""; // 参数
}

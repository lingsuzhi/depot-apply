package com.lsz.depot.framework.annotation;

import java.lang.annotation.*;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AspectExt {

    //false为不认证
    boolean auth() default true;

    //默认是 dev环境会在控制台打日志
    String logLevel() default "";
}

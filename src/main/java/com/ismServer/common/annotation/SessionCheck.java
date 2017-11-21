package com.ismServer.common.annotation;

import java.lang.annotation.*;

/**
 * session验证注解
 *
 * @author Terry Zhang
 * @date 2017-09-19 九月 21:04
 * @modify
 **/
@Target(value = ElementType.METHOD)
@Retention(value = RetentionPolicy.RUNTIME)
@Documented
public @interface SessionCheck {
    boolean value() default true;
}

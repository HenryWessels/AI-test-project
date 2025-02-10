package com.ginger.aitest.infrastructure.aop.pointcutAnnotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ThreadName {

    String customThreadName() default "";
    boolean useCorrelationId() default false;
}

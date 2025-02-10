package com.ginger.aitest.infrastructure.aop.pointcutAnnotations;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AroundThreadName {

    String customThreadName() default "";
    boolean useCorrelationId() default false;

    boolean temporaryName() default false;
}

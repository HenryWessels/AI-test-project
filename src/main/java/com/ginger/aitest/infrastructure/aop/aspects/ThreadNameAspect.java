package com.ginger.aitest.infrastructure.aop.aspects;

import com.ginger.aitest.infrastructure.aop.pointcutAnnotations.AroundThreadName;
import com.ginger.aitest.infrastructure.aop.pointcutAnnotations.ThreadName;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.UUID;

@Aspect
@Component
public class ThreadNameAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("@annotation(com.ginger.aitest.infrastructure.aop.pointcutAnnotations.ThreadName)")
    public void changeThreadName(JoinPoint joinPoint) {
        String newThreadName = getBeforeThreadName(joinPoint);
        logger.trace("Changing thread name from [{}] to [{}].", Thread.currentThread().getName(), newThreadName);
        Thread.currentThread().setName(newThreadName);
    }

    @Around("@annotation(com.ginger.aitest.infrastructure.aop.pointcutAnnotations.AroundThreadName)")
    public Object changeThreadName(ProceedingJoinPoint joinPoint) throws Throwable {
        String oldThreadName = Thread.currentThread().getName();
        String newThreadName = getAroundThreadName(joinPoint);
        logger.trace("Changing thread name from [{}](current) to [{}].", oldThreadName, newThreadName);
        Thread.currentThread().setName(newThreadName);

        Object value = joinPoint.proceed();

        if (((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(AroundThreadName.class).temporaryName()) {
            logger.trace("Changing thread name back to [{}] from [{}](current).", newThreadName, oldThreadName);
            Thread.currentThread().setName(oldThreadName);
            logger.trace("Finished Changing thread name to [{}].", oldThreadName);
        }
        return value;
    }

    private String getBeforeThreadName(JoinPoint joinPoint) {
        String threadName = "Pre-Defined: " + UUID.randomUUID();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        ThreadName pointCutAnnotation = methodSignature.getMethod().getAnnotation(ThreadName.class);

        if (!pointCutAnnotation.customThreadName().isEmpty()) {
            threadName = pointCutAnnotation.customThreadName();
            logger.trace("Thread name pulled from annotation.");
        } if (pointCutAnnotation.useCorrelationId()) {
            threadName = extractCorrelationId(methodSignature, joinPoint, threadName);
        }
        logger.trace("Thread name will be set to [{}].", threadName);
        return threadName;
    }

    private String getAroundThreadName(ProceedingJoinPoint joinPoint) {
        String threadName = "Pre-Defined: " + UUID.randomUUID();
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        AroundThreadName pointCutAnnotation = methodSignature.getMethod().getAnnotation(AroundThreadName.class);

        if (!pointCutAnnotation.customThreadName().isEmpty()) {
            threadName = pointCutAnnotation.customThreadName();
            logger.trace("Thread name pulled from annotation.");
        } if (pointCutAnnotation.useCorrelationId()) {
            threadName = extractCorrelationId(methodSignature, joinPoint, threadName);
        }
        logger.trace("Thread name will be set to [{}].", threadName);
        return threadName;
    }

    private String extractCorrelationId(MethodSignature methodSignature, JoinPoint joinPoint, String threadName) {
        logger.trace("Thread name should be derived from CorrelationID.");
        try {
            int index = Arrays.stream(methodSignature.getParameterNames()).toList().indexOf("correlationId");
            threadName = Arrays.stream(joinPoint.getArgs()).toList().get(index).toString();
        } catch (Exception ignored) {
            logger.warn("Failed to derived Thread name from CorrelationID.");
        }
        return threadName;
    }
}

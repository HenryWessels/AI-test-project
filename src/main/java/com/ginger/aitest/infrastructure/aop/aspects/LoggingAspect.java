package com.ginger.aitest.infrastructure.aop.aspects;

import com.ginger.aitest.infrastructure.aop.pointcuts.Logging;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.time.LocalDateTime;

import static java.time.Duration.between;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Around(value = "@annotation(com.ginger.aitest.infrastructure.aop.pointcuts.Logging)")
    public Object afterReturningAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        logger.trace("Logging time taken for method {}", proceedingJoinPoint.getSignature().getName());

        LocalDateTime before = LocalDateTime.now();
        Object value = proceedingJoinPoint.proceed();

        logTimeTaken(proceedingJoinPoint, before);
        return value;
    }

    private void logTimeTaken(ProceedingJoinPoint proceedingJoinPoint, LocalDateTime before) {
        MethodSignature methodSignature = (MethodSignature) proceedingJoinPoint.getSignature();
        Logging logging = methodSignature.getMethod().getAnnotation(Logging.class);

        String methodName = logging.methodName().isEmpty() ? methodSignature.getName() : logging.methodName();
        String operations = logging.operations();

        long timeTaken = between(before, LocalDateTime.now()).toMillis();
        long maxTime = logging.maxTime();

        boolean operationsB = !logging.operations().isEmpty();
        boolean maxTimeB = logging.maxTime() > 0;
        boolean timeExceeded = maxTimeB && timeTaken > maxTime;

        StringBuilder s = new StringBuilder("Method: [{}] took {}ms to execute.");
        if (maxTimeB)
            s.append(timeExceeded ? "   Max time for method: {}ms - exceeded by {}ms." : "   Max time for method: {}ms.");
        if (operationsB)
            s.append("   Operations executed: [{}].");

        if (timeExceeded && operationsB)
            logger.warn(s.toString(), methodName, formatTime(timeTaken), maxTime, (timeTaken - maxTime), operations);
        else if (maxTimeB && operationsB)
            logger.info(s.toString(), methodName, formatTime(timeTaken), maxTime, operations);
        else if (maxTimeB)
            logger.info(s.toString(), methodName, formatTime(timeTaken), maxTime);
        else if (operationsB)
            logger.info(s.toString(), methodName, formatTime(timeTaken), operations);
        else
            logger.info(s.toString(), methodName, formatTime(timeTaken));
    }

    public String formatTime(long timeTaken) {
        return new DecimalFormat("#,###").format(timeTaken);
    }
}
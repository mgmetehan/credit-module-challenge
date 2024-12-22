package com.mgmetehan.credit_module_challenge.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class ServiceLoggingAspect {

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {
        // Pointcut for all classes annotated with @Service
    }

    @Around("serviceMethods()")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] methodArgs = joinPoint.getArgs();

        log.info("Entering method: {}.{} with arguments: {}", className, methodName, methodArgs);

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Exception in method: {}.{} with message: {}", className, methodName, throwable.getMessage());
            throw throwable;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("Exiting method: {}.{} with result: {} in {} ms", className, methodName, result, elapsedTime);
        return result;
    }
}

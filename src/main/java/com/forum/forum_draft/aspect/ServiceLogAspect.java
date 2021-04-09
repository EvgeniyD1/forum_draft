package com.forum.forum_draft.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Component
@Aspect
@Slf4j
public class ServiceLogAspect {

    @Pointcut("execution(* com.forum.forum_draft.service..*(..))")
    public void serviceLog() {
        /*pointcut*/
    }

    @Around("serviceLog()")
    public Object logAroundMethods(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch sw = new StopWatch();
        log.info("Method " + joinPoint.getSignature().getDeclaringTypeName() + " " +
                joinPoint.getSignature().getName() + " start");
        sw.start();
        Object proceed = joinPoint.proceed();
        sw.stop();
        log.info("Method " + joinPoint.getSignature().getDeclaringTypeName() + " " +
                joinPoint.getSignature().getName() + " finished");
        log.info("Method execution time: " + sw.getTotalTimeMillis() + " milliseconds.");
        return proceed;
    }

}

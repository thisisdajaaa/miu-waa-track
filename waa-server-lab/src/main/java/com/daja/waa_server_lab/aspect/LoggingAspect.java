package com.daja.waa_server_lab.aspect;

import com.daja.waa_server_lab.entity.dto.request.ExceptionLoggerDto;
import com.daja.waa_server_lab.entity.dto.request.LoggerDto;
import com.daja.waa_server_lab.service.spec.IExceptionLoggerService;
import com.daja.waa_server_lab.service.spec.ILoggerService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.UUID;

@Aspect
@Component
public class LoggingAspect {
    private static final String PRINCIPLE = "staticUser";

    private final ILoggerService loggerService;

    private final IExceptionLoggerService exceptionLoggerService;

    public LoggingAspect(ILoggerService loggerService, IExceptionLoggerService exceptionLoggerService) {
        this.loggerService = loggerService;
        this.exceptionLoggerService = exceptionLoggerService;
    }

    @Pointcut("execution(* com.daja.waa_server_lab.controller.*.*(..))")
    public void applicationPackagePointcut() {
    }

    @Before("applicationPackagePointcut()")
    public void logBeforeMethod(JoinPoint joinPoint) {
        LoggerDto loggerDto = LoggerDto.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(LocalDateTime.now())
                .principle(PRINCIPLE)
                .operation(joinPoint.getSignature().getName())
                .build();

        loggerService.add(loggerDto);
    }

    @Around("@annotation(com.daja.waa_server_lab.aspect.annotation.ExecutionTime)")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object proceed = joinPoint.proceed();
        long executionTime = System.currentTimeMillis() - start;

        System.out.println(joinPoint.getSignature() + " executed in " + executionTime + "ms");
        return proceed;
    }

    @AfterThrowing(pointcut = "applicationPackagePointcut()", throwing = "ex")
    public void logAfterThrowing(JoinPoint joinPoint, Throwable ex) {
        ExceptionLoggerDto exceptionLoggerDto = ExceptionLoggerDto.builder()
                .transactionId(UUID.randomUUID().toString())
                .dateTime(LocalDateTime.now())
                .principle(PRINCIPLE)
                .operation(joinPoint.getSignature().getName())
                .exceptionType(ex.getClass().getName())
                .build();

        exceptionLoggerService.add(exceptionLoggerDto);
    }
}

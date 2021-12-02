package com.codegym.cms.logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Aspect
@Component
public class Logger {
    @AfterThrowing(pointcut = "execution(public * com.codegym.cms.service.CustomerService.findAll(..))")
    public void error(){
        System.out.println("[CMS] ERROR");
    }
    @AfterReturning(pointcut = "within(com.codegym.cms.controller.*)", returning = "result")
    public void log(JoinPoint joinPoint, Object result){
        System.out.println("Start log");
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        System.out.println(className + "." + methodName);
        if(result == null){
            System.out.println("null");
        }
        else {
            System.out.println(result.hashCode());
            System.out.println(LocalDate.now());
        }
    }
}

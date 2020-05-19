package com.talentcard.web.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author ChenXU
 * @version 1.0
 * @createTime 2020-05-08 16:53
 * @description
 */
@Aspect
@Component
public class LogInterceptor {
//    @Pointcut("execution(public * com.talentcard.web.controller.*.*(..))")
//    public void log() {
//    }
//    @Before("log()")
//    public void deoBefore(JoinPoint joinPoint) {
//        System.out.println("!!!!!!!!!!!!");
//    }
}

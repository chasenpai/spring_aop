package com.springaopexample.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class ControllerAspect {

    @Around("com.springaopexample.aop.Pointcuts.allController()")
    public Object inControllerLayer(ProceedingJoinPoint joinPoint) throws Throwable {

        StopWatch stopWatch = new StopWatch();

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        try{
            stopWatch.start();
            return joinPoint.proceed();
        } catch (Throwable ex){
            throw ex;
        }finally{
            stopWatch.stop();
            log.info("request = {}, ip = {}, duration time = {}",
                    getRequestUri(request), request.getRemoteAddr(), stopWatch.getTotalTimeMillis());
        }

    }

    private String getRequestUri(HttpServletRequest request){
        return String.format("@%s %s", request.getMethod(), request.getRequestURI());
    }

}

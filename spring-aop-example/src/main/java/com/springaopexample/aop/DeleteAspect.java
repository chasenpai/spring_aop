package com.springaopexample.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Aspect
@Component
@Slf4j
public class DeleteAspect {

    @After("@annotation(com.springaopexample.annotation.Delete)")
    public void delete(JoinPoint joinPoint){

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder
                .getRequestAttributes()).getRequest();

        String ip = request.getRemoteAddr();
        Long id = getDeleteRequestItem(joinPoint);

        log.warn("delete request ip = {}, product id = {}", ip, id);

    }

    private Long getDeleteRequestItem(JoinPoint joinPoint){

        for(Object arg : joinPoint.getArgs()){
            if(arg instanceof Long){
                Long id = (Long) arg;
                return id;
            }
        }

        return null;
    }

}

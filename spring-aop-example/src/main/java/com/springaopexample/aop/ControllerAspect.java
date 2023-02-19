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

        /**
         * JoinPoint
         * - 어드바이스가 적용될 수 있는 위치
         * - 스프링 AOP 는 프록시 방식을 사용하기 떄문에 메소드 실행 지점으로 제한된다
         *
         * JoinPoint & ProceedingJoinPoint 인터페이스
         */
        log.info("Signature = {}", joinPoint.getSignature()); //메소드 시그니처(이름, 매개변수 등) 리턴
        log.info("Object = {}", joinPoint.getTarget()); //해당 클래스 객체 리턴
        log.info("Object[] = {}", joinPoint.getArgs()); //메소드에 넘긴 인자 목록을 리턴

        try{
            stopWatch.start();
            //Before
            return joinPoint.proceed(); //메소드를 실행하고 리턴값을 받는다.
            //after
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

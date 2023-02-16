package com.springaopexample.aop;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {

    @Pointcut("execution(* com.springaopexample.controller.*Controller.*(..))")
    public void allController(){}

    @Pointcut("@annotation(com.springaopexample.annotation.Delete)")
    public void delete(){}

}

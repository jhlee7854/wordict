package kr.pe.jady.wordict.config.spring.aop.pointcut;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class ExceptionLoggingPointcut {
    @Pointcut("execution(* kr.pe.jady.wordict..service.*.*(..))")
    public void serviceOperation() {}
}

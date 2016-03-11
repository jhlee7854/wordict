package kr.pe.jady.wordict.config.spring.aop.advice;

import kr.pe.jady.wordict.domain.model.SystemException;
import kr.pe.jady.wordict.system.repository.SystemExceptionRepository;
import org.apache.commons.lang.SystemUtils;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class ExceptionLoggingAdvice {

    private SystemExceptionRepository systemExceptionRepository;

    @Autowired
    public void setSystemExceptionRepository(SystemExceptionRepository systemExceptionRepository) {
        this.systemExceptionRepository = systemExceptionRepository;
    }

    @AfterThrowing(
            pointcut = "kr.pe.jady.wordict.config.spring.aop.pointcut.ExceptionLoggingPointcut.serviceOperation()",
            throwing = "ex")
    public void logSystemException(Exception ex) {
        SystemException systemException = new SystemException();
        systemException.setExceptionClassName(ex.getClass().getName());
        systemException.setMessage(ex.getMessage());
        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : ex.getStackTrace()) {
            stackTrace.append(element.toString()).append(SystemUtils.LINE_SEPARATOR);
        }
        systemException.setStackTrace(stackTrace.toString());

        systemExceptionRepository.save(systemException);
    }
}

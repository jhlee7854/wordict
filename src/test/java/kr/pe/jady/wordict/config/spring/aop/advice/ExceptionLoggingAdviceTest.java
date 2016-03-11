package kr.pe.jady.wordict.config.spring.aop.advice;

import kr.pe.jady.wordict.config.spring.app.AppConfig;
import kr.pe.jady.wordict.config.spring.app.DataSourceConfig;
import kr.pe.jady.wordict.config.spring.app.JpaConfig;
import kr.pe.jady.wordict.config.spring.app.TransactionConfig;
import kr.pe.jady.wordict.domain.model.SystemException;
import kr.pe.jady.wordict.system.repository.SystemExceptionRepository;
import org.apache.commons.lang.SystemUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, DataSourceConfig.class, JpaConfig.class, TransactionConfig.class})
@Transactional
public class ExceptionLoggingAdviceTest {

    @Autowired
    private SystemExceptionRepository systemExceptionRepository;

    @Test
    public void testLogSystemException() {
        String expectedExceptionMessage = "예외 처리 테스트";
        Exception exception = new NullPointerException(expectedExceptionMessage);

        StringBuilder stackTrace = new StringBuilder();
        for (StackTraceElement element : exception.getStackTrace()) {
            stackTrace.append(element.toString()).append(SystemUtils.LINE_SEPARATOR);
        }
        String expectedStackTrace = stackTrace.toString();

        ExceptionLoggingAdvice advice = new ExceptionLoggingAdvice();
        advice.setSystemExceptionRepository(systemExceptionRepository);

        advice.logSystemException(exception);

        List<SystemException> list = systemExceptionRepository.findAll();

        assertEquals("예외 저장 내역 확인", 1, list.size());
        assertEquals("예외 클래스명 비교", exception.getClass().getName(), list.get(0).getExceptionClassName());
        assertEquals("예외 메시지 비교", expectedExceptionMessage, list.get(0).getMessage());
        assertEquals("예외 stack trace 비교", expectedStackTrace, list.get(0).getStackTrace());
    }
}

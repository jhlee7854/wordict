package kr.pe.jady.wordict.config.spring.aop.pointcut;

import kr.pe.jady.wordict.config.spring.aop.advice.ExceptionLoggingAdvice;
import kr.pe.jady.wordict.config.spring.app.AppConfig;
import kr.pe.jady.wordict.config.spring.app.DataSourceConfig;
import kr.pe.jady.wordict.config.spring.app.JpaConfig;
import kr.pe.jady.wordict.config.spring.app.TransactionConfig;
import kr.pe.jady.wordict.domain.model.SystemException;
import kr.pe.jady.wordict.system.repository.SystemExceptionRepository;
import kr.pe.jady.wordict.user.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class, ExceptionLoggingPointcut.class, ExceptionLoggingAdvice.class, DataSourceConfig.class, JpaConfig.class, TransactionConfig.class})
@Transactional
public class ExceptionLoggingPointcutTest {

    @Autowired
    private UserService userService;

    @Autowired
    private SystemExceptionRepository systemExceptionRepository;

    @Test
    public void testSavingExceptionInfoAtFindBySearchConditionsWithNullArgument() {
        try {
            userService.findBySearchConditions(null);
        } catch (IllegalArgumentException e) {
            List<SystemException> list = systemExceptionRepository.findAll();

            assertEquals("예외 저장 내역", 1, list.size());
        }
    }
}

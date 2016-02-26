package kr.pe.jady.wordict.user.repository;

import kr.pe.jady.wordict.config.spring.app.DataSourceConfig;
import kr.pe.jady.wordict.config.spring.app.JpaConfig;
import kr.pe.jady.wordict.config.spring.app.TransactionConfig;
import kr.pe.jady.wordict.domain.model.User;
import kr.pe.jady.wordict.user.vo.UserSearchVo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfig.class, JpaConfig.class, TransactionConfig.class})
@Transactional
public class UserRepositoryTest {

    private static Integer NUMBER_OF_USERS = 3;
    private User user1;
    private User user2;
    private User user3;

    @Autowired
    private UserRepository repository;

    @Before
    public void setUp() throws ParseException {
        assertNotNull(repository);
        user1 = repository.save(new User("홍길동", "gdHong@jady.pe.kr", new SimpleDateFormat("yyyyMMddHHmmss").parse("20160102000000")));
        user2 = repository.save(new User("임꺾정", "kjYim@jady.pe.kr", new SimpleDateFormat("yyyyMMddHHmmss").parse("20160115000000")));
        user3 = repository.save(new User("장길산", "ksJang@jady.pe.kr", new SimpleDateFormat("yyyyMMddHHmmss").parse("20160202000000")));
    }

    @After
    public void tearDown() {

    }

    @Test
    public void testFindAll() {
        List<User> userList = repository.findAll();
        assertEquals("전체 사용자의 수", NUMBER_OF_USERS.intValue(), userList.size());
    }

    @Test
    public void testFindOne() {
        User foundUser = repository.findOne(user1.getId());
        assertEquals("조회한 사용자 비교", user1, foundUser);
    }

    @Test
    public void testSaveNewUser() {
        User newUser = new User("일지매", "eeljimae@jady.pe.kr", new Date());

        newUser = repository.save(newUser);
        assertNotNull("추가된 사용자는 아이디가 존재해야 한다.", newUser.getId());

        Long count = repository.count();
        assertEquals("전체 사용자의 수 증가", Long.valueOf(NUMBER_OF_USERS + 1), count);
    }

    @Test
    public void testDelete() {
        repository.delete(user1);

        Long count = repository.count();
        assertEquals("전체 사용자의 수 감소", Long.valueOf(NUMBER_OF_USERS - 1), count);
    }

    @Test
    public void testSearchUserByDateRange() throws ParseException {
        UserSearchVo vo = new UserSearchVo();
        vo.setStartDt(new SimpleDateFormat("yyyyMMddHHmmss").parse("20160110000000"));
        vo.setEndDt(new SimpleDateFormat("yyyyMMddHHmmss").parse("20160120000000"));

        List<User> userList = repository.findAllByGeneratedDateRange(vo);

        assertEquals("검색된 사용자의 수", 1, userList.size());
        assertEquals("검색된 사용자 비교", user2, userList.get(0));
    }

    @Test
    public void testFindByGeneratedBetween() throws ParseException {
        UserSearchVo vo = new UserSearchVo();
        vo.setStartDt(new SimpleDateFormat("yyyyMMddHHmmss").parse("20160110000000"));
        vo.setEndDt(new SimpleDateFormat("yyyyMMddHHmmss").parse("20160120000000"));

        List<User> userList = repository.findByGeneratedBetween(vo.getStartDt(), vo.getEndDt());

        assertEquals("검색된 사용자의 수", 1, userList.size());
        assertEquals("검색된 사용자 비교", user2, userList.get(0));
    }
}

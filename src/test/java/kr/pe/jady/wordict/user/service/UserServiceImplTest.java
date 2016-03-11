package kr.pe.jady.wordict.user.service;

import kr.pe.jady.wordict.domain.model.User;
import kr.pe.jady.wordict.user.repository.UserRepository;
import kr.pe.jady.wordict.user.vo.UserSearchVo;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.text.ParseException;
import java.util.List;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class UserServiceImplTest {

    @InjectMocks
    private UserService userService;

    @Mock
    private UserRepository repository;

    @Before
    public void setUp() throws ParseException {
        userService = new UserServiceImpl();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindBySearchConditionsWithNoneConditions() {
        UserSearchVo userSearchVo = new UserSearchVo();
        List<User> actualUserList = userService.findBySearchConditions(userSearchVo);

        assertNotNull("사용자 목록조회 메서드는 사용자 목록을 반환해야 한다.", actualUserList);
        assertThat("사용자 목록조회 메서드는 사용자 목록을 반환해야 한다.", actualUserList, instanceOf(List.class));

        verify(repository, times(1)).findAll();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFindBySearchConditionsWithNullArgument() {
        userService.findBySearchConditions(null);
    }
}

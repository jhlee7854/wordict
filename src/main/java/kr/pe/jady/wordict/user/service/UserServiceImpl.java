package kr.pe.jady.wordict.user.service;

import kr.pe.jady.wordict.domain.model.User;
import kr.pe.jady.wordict.user.repository.UserRepository;
import kr.pe.jady.wordict.user.vo.UserSearchVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findBySearchConditions(UserSearchVo userSearchVo) {
        if (userSearchVo == null) throw new IllegalArgumentException("입력값이 올바르지 않습니다.");
        return userRepository.findAll();
    }
}

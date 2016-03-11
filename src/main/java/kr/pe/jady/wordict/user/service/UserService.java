package kr.pe.jady.wordict.user.service;

import kr.pe.jady.wordict.domain.model.User;
import kr.pe.jady.wordict.user.vo.UserSearchVo;

import java.util.List;

public interface UserService {
    public List<User> findBySearchConditions(UserSearchVo userSearchVo);
}

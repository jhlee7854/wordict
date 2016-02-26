package kr.pe.jady.wordict.user.repository;

import kr.pe.jady.wordict.domain.model.User;
import kr.pe.jady.wordict.user.vo.UserSearchVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("select u from kr.pe.jady.wordict.domain.model.User u where u.generated > :#{#userSearchVo.startDt} and u.generated < :#{#userSearchVo.endDt}")
    List<User> findAllByGeneratedDateRange(@Param("userSearchVo") UserSearchVo userSearchVo);

    List<User> findByGeneratedBetween(Date startDate, Date endDate);
}

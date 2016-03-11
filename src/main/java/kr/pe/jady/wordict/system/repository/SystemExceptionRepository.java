package kr.pe.jady.wordict.system.repository;

import kr.pe.jady.wordict.domain.model.SystemException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemExceptionRepository extends JpaRepository<SystemException, Integer> {
}

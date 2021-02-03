package am.itspace.restapi.repository;

import am.itspace.restapi.model.LastLogin;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LastLoginRepository extends JpaRepository<LastLogin, Integer> {

    List<LastLogin> findByUserId(int userId);

}

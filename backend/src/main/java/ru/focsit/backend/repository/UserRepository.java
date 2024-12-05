package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.focsit.backend.pojo.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUserLogin(String userLogin);
    @Query("SELECT u FROM User u JOIN FETCH u.userRole WHERE u.userLogin = :username")
    Optional<User> findByUserLoginWithRole(@Param("username") String username);
}

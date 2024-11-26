package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.User;

public interface UserRepository extends JpaRepository<User, Long> {
}

package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Concert;
import ru.focsit.backend.pojo.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
}

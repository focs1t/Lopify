package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Concert;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
}

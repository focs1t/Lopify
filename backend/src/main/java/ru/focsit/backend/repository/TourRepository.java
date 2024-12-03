package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Tour;

public interface TourRepository extends JpaRepository<Tour, Long> {
}

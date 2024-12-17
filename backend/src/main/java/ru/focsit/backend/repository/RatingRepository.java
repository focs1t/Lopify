package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {
}

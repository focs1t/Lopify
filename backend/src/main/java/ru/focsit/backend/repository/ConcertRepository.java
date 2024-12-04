package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Concert;
import ru.focsit.backend.pojo.Tour;

import java.util.List;

public interface ConcertRepository extends JpaRepository<Concert, Long> {
    List<Concert> findByConcertTour(Tour tour);
}

package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Tour;

import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    List<Tour> findByTourArtist(Artist artist);
}

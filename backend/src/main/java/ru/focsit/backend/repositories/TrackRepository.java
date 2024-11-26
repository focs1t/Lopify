package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Track;

public interface TrackRepository extends JpaRepository<Track, Long> {
}

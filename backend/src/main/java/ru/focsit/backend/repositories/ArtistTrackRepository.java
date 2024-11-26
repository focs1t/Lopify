package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.ArtistTrack;

public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, Long> {
}

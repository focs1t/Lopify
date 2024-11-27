package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.ArtistTrack;
import ru.focsit.backend.pojo.ArtistTrackId;

public interface ArtistTrackRepository extends JpaRepository<ArtistTrack, ArtistTrackId> {
}

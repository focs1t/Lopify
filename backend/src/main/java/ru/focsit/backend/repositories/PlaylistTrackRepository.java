package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.PlaylistTrack;
import ru.focsit.backend.pojo.PlaylistTrackId;

public interface PlaylistTrackRepository extends JpaRepository<PlaylistTrack, PlaylistTrackId> {
}

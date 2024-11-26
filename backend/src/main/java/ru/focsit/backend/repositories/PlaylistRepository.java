package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Playlist;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
}

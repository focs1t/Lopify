package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Playlist;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByUserId(Long userId);
}
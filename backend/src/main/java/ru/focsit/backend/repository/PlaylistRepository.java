package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Song;

import java.util.List;

public interface PlaylistRepository extends JpaRepository<Playlist, Long> {
    Playlist findByUserId(Long userId);

    @Modifying
    @Transactional
    @Query("DELETE FROM Playlist p WHERE :song MEMBER OF p.songs")
    void removeSongFromAllPlaylists(@Param("song") Song song);
}
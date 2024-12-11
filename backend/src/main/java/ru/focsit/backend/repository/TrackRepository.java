package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.*;

import java.util.List;

public interface TrackRepository extends JpaRepository<Track, Long> {
    List<Track> findByTrackAlbum(Album album);
    List<Track> findByPlaylists(Playlist playlist);

    List<Track> findByTrackNameContainingIgnoreCase(String query);
}

package ru.focsit.backend.services;

import ru.focsit.backend.pojo.PlaylistTrack;
import ru.focsit.backend.pojo.PlaylistTrackId;
import ru.focsit.backend.repositories.PlaylistTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistTrackService {

    @Autowired
    private PlaylistTrackRepository playlistTrackRepository;

    public List<PlaylistTrack> getAllPlaylistTracks() {
        return playlistTrackRepository.findAll();
    }

    public PlaylistTrack createPlaylistTrack(PlaylistTrack playlistTrack) {
        return playlistTrackRepository.save(playlistTrack);
    }

    public void deletePlaylistTrack(Long playlistId, Long trackId) {
        playlistTrackRepository.deleteById(new PlaylistTrackId(playlistId, trackId));
    }
}

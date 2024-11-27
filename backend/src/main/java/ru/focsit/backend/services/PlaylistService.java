package ru.focsit.backend.services;

import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.repositories.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Long playlistId, Playlist playlistDetails) {
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (playlist.isPresent()) {
            Playlist curPlaylist = playlist.get();
            curPlaylist.setPlaylistName(playlistDetails.getPlaylistName());
            curPlaylist.setPlaylistDescription(playlistDetails.getPlaylistDescription());
            curPlaylist.setPlaylistImageUrl(playlistDetails.getPlaylistImageUrl());
            curPlaylist.setPlaylistUser(playlistDetails.getPlaylistUser());
            return playlistRepository.save(curPlaylist);
        }
        return null;
    }

    public void deletePlaylist(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }
}

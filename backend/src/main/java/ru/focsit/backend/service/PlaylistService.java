package ru.focsit.backend.service;

import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.PlaylistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TrackService trackService;

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

            List<Track> currentTracks = trackService.getTracksByPlaylist(curPlaylist);
            List<Track> newTracks = playlistDetails.getTracks();
            currentTracks.stream()
                    .filter(currentTrack -> !newTracks.contains(currentTrack))
                    .forEach(currentTrack -> {
                        currentTrack.getPlaylists().remove(curPlaylist);
                        if (currentTrack.getPlaylists().isEmpty()) {
                            trackService.deleteTrack(currentTrack.getTrackId());
                        } else {
                            trackService.updateTrack(currentTrack.getTrackId(), currentTrack);
                        }
                    });
            newTracks.stream()
                    .filter(newTrack -> !currentTracks.contains(newTrack))
                    .forEach(newTrack -> {
                        newTrack.getPlaylists().add(curPlaylist);
                        if (newTrack.getTrackId() == null) {
                            trackService.createTrack(newTrack);
                        } else {
                            trackService.updateTrack(newTrack.getTrackId(), newTrack);
                        }
                    });

            return playlistRepository.save(curPlaylist);
        }
        return null;
    }

    public void deletePlaylist(Long playlistId) {
        playlistRepository.deleteById(playlistId);
    }

    public List<Playlist> getPlaylistsByUser(User user) {
        return getAllPlaylists().stream()
                .filter(playlist -> playlist.getPlaylistUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<Playlist> searchPlaylists(String query) {
        return getAllPlaylists().stream()
                .filter(playlist -> playlist.getPlaylistName().contains(query))
                .collect(Collectors.toList());
    }

    public Playlist getFirstPlaylist() {
        return getAllPlaylists().get(0);
    }

    public List<Playlist> searchPlaylistsByUser(User curUser, String query) {
        return getAllPlaylists().stream()
                .filter(playlist -> playlist.getPlaylistUser().equals(curUser) && playlist.getPlaylistUser().getUserLogin().contains(query))
                .collect(Collectors.toList());
    }
}

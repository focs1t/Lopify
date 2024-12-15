package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.PlaylistRepository;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private TrackService trackService;

    @Autowired
    private FileUploadService fileUploadService;

    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    public Optional<Playlist> getPlaylistById(Long playlistId) {
        return playlistRepository.findById(playlistId);
    }

    public Playlist createPlaylist(@Valid Playlist playlist, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String filePath = fileUploadService.uploadFile(file);
            playlist.setPlaylistImageUrl(filePath);
        }
        return playlistRepository.save(playlist);
    }

    public Playlist updatePlaylist(Long playlistId, @Valid Playlist playlistDetails, MultipartFile file) {
        Optional<Playlist> playlistOptional = playlistRepository.findById(playlistId);
        if (playlistOptional.isPresent()) {
            Playlist curPlaylist = playlistOptional.get();
            curPlaylist.setPlaylistName(playlistDetails.getPlaylistName());
            curPlaylist.setPlaylistDescription(playlistDetails.getPlaylistDescription());
            if (file != null && !file.isEmpty()) {
                String filePath = fileUploadService.uploadFile(file);
                curPlaylist.setPlaylistImageUrl(filePath);
            }
            curPlaylist.setPlaylistUser(playlistDetails.getPlaylistUser());

            List<Track> currentTracks = curPlaylist.getTracks();
            List<Track> newTracks = playlistDetails.getTracks();

            newTracks.stream()
                    .filter(newTrack -> !currentTracks.contains(newTrack))
                    .forEach(newTrack -> newTrack.getPlaylists().add(curPlaylist));

            currentTracks.stream()
                    .filter(currentTrack -> !newTracks.contains(currentTrack))
                    .forEach(currentTrack -> currentTrack.getPlaylists().remove(curPlaylist));

            updatePlaylistDuration(curPlaylist);

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
                .filter(playlist -> playlist.getPlaylistUser().equals(curUser) && playlist.getPlaylistUser().getUsername().contains(query))
                .collect(Collectors.toList());
    }

    private void updatePlaylistDuration(Playlist playlist) {
        Duration totalDuration = playlist.getTracks().stream()
                .map(Track::getTrackDuration)
                .map(duration -> Duration.between(LocalTime.MIN, duration))
                .reduce(Duration.ZERO, Duration::plus);
        playlist.setPlaylistDuration(LocalTime.ofNanoOfDay(totalDuration.toNanos()));
    }
}
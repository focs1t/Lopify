package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.PlaylistRepository;

import jakarta.validation.Valid;
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
        Optional<Playlist> playlist = playlistRepository.findById(playlistId);
        if (playlist.isPresent()) {
            Playlist curPlaylist = playlist.get();
            curPlaylist.setPlaylistName(playlistDetails.getPlaylistName());
            curPlaylist.setPlaylistDescription(playlistDetails.getPlaylistDescription());
            if (file != null && !file.isEmpty()) {
                String filePath = fileUploadService.uploadFile(file);
                curPlaylist.setPlaylistImageUrl(filePath);
            }
            curPlaylist.setPlaylistDuration(playlistDetails.getPlaylistDuration());
            curPlaylist.setPlaylistUser(playlistDetails.getPlaylistUser());
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
}
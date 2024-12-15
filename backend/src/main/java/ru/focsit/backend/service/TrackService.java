package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.repository.TrackRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private FileUploadService fileUploadService;

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Optional<Track> getTrackById(Long trackId) {
        return trackRepository.findById(trackId);
    }

    public Track createTrack(@Valid Track track, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String filePath = fileUploadService.uploadFile(file);
            track.setTrackImageUrl(filePath);
        }
        return trackRepository.save(track);
    }

    public void deleteTrack(Long trackId) {
        trackRepository.deleteById(trackId);
    }

    public List<Track> searchTracks(String query) {
        return getAllTracks().stream()
                .filter(track -> track.getTrackName().contains(query) ||
                        track.getArtists().stream().anyMatch(artist -> artist.getArtistName().contains(query)))
                .toList();
    }

    public List<Track> getTracksByAlbum(Album album) {
        return getAllTracks().stream()
                .filter(track -> track.getTrackAlbum().equals(album))
                .toList();
    }

    public List<Track> searchTracksByAlbum(Album album, String query) {
        return getTracksByAlbum(album).stream()
                .filter(track -> track.getTrackName().contains(query) ||
                        track.getArtists().stream().anyMatch(artist -> artist.getArtistName().contains(query)))
                .toList();
    }

    public List<Track> getTracksByArtist(Artist artist) {
        return getAllTracks().stream()
                .filter(track -> track.getArtists().contains(artist))
                .toList();
    }

    public List<Track> searchTracksByArtist(Artist artist, String query) {
        return getTracksByArtist(artist).stream()
                .filter(track -> track.getTrackName().contains(query))
                .toList();
    }

    public List<Track> getTracksByPlaylist(Playlist playlist) {
        return getAllTracks().stream()
                .filter(track -> track.getPlaylists().contains(playlist))
                .toList();
    }

    public List<Track> searchTracksByPlaylist(Playlist playlist, String query) {
        return getTracksByPlaylist(playlist).stream()
                .filter(track -> track.getTrackName().contains(query) ||
                        track.getArtists().stream().anyMatch(artist -> artist.getArtistName().contains(query)))
                .toList();
    }
}
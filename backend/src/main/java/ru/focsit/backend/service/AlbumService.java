package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Genre;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.repository.AlbumRepository;

import jakarta.validation.Valid;
import java.time.Duration;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackService trackService;

    @Autowired
    private FileUploadService fileUploadService;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long albumId) {
        return albumRepository.findById(albumId);
    }

    public Album createAlbum(@Valid Album album, MultipartFile file) {
        if (file != null && !file.isEmpty()) {
            String filePath = fileUploadService.uploadFile(file);
            album.setAlbumImageUrl(filePath);
        }
        Album savedAlbum = albumRepository.save(album);
        if (album.getTracks() != null) {
            album.getTracks().stream()
                    .peek(track -> track.setTrackAlbum(savedAlbum))
                    .forEach(track -> trackService.createTrack(track, null, savedAlbum));

            updateAlbumDuration(savedAlbum);
        }
        return albumRepository.save(savedAlbum);
    }

    public void deleteAlbum(Long albumId) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            List<Track> tracks = trackService.getTracksByAlbum(album);
            if (tracks.isEmpty()) {
                albumRepository.deleteById(albumId);
            }
        }
    }

    public List<Album> searchAlbums(String query) {
        return getAllAlbums().stream()
                .filter(album -> album.getAlbumName().contains(query))
                .collect(Collectors.toList());
    }

    public List<Album> getAlbumsByArtist(Artist artist) {
        return getAllAlbums().stream()
                .filter(album -> album.getAlbumArtist().equals(artist))
                .collect(Collectors.toList());
    }

    public List<Album> searchAlbumsByArtist(Artist artist, String query) {
        return getAllAlbums().stream()
                .filter(album -> album.getAlbumArtist().equals(artist) || album.getAlbumName().contains(query))
                .collect(Collectors.toList());
    }

    public Map<String, List<Album>> getAlbumsByAllGenres(List<Genre> genres) {
        return genres.stream()
                .collect(Collectors.toMap(
                        Genre::getGenreName,
                        genre -> getAllAlbums().stream()
                                .filter(album -> album.getAlbumGenre().equals(genre))
                                .collect(Collectors.toList())
                ));
    }

    private void updateAlbumDuration(Album album) {
        Duration totalDuration = album.getTracks().stream()
                .map(Track::getTrackDuration)
                .map(duration -> Duration.between(LocalTime.MIN, duration))
                .reduce(Duration.ZERO, Duration::plus);
        album.setAlbumDuration(LocalTime.ofNanoOfDay(totalDuration.toNanos()));
    }
}
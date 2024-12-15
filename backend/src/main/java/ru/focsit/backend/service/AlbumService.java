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
                    .forEach(track -> trackService.createTrack(track, null));
        }
        return savedAlbum;
    }

    public Album updateAlbum(Long albumId, @Valid Album albumDetails, MultipartFile file) {
        Optional<Album> albumOptional = albumRepository.findById(albumId);
        if (albumOptional.isPresent()) {
            Album curAlbum = albumOptional.get();
            curAlbum.setAlbumName(albumDetails.getAlbumName());
            curAlbum.setAlbumDescription(albumDetails.getAlbumDescription());
            if (file != null && !file.isEmpty()) {
                String filePath = fileUploadService.uploadFile(file);
                curAlbum.setAlbumImageUrl(filePath);
            }
            curAlbum.setAlbumReleaseDate(albumDetails.getAlbumReleaseDate());
            curAlbum.setAlbumDuration(albumDetails.getAlbumDuration());
            curAlbum.setAlbumArtist(albumDetails.getAlbumArtist());
            curAlbum.setAlbumGenre(albumDetails.getAlbumGenre());

            List<Track> currentTracks = trackService.getTracksByAlbum(curAlbum);
            List<Track> newTracks = albumDetails.getTracks();

            currentTracks.stream()
                    .filter(currentTrack -> !newTracks.contains(currentTrack))
                    .forEach(currentTrack -> trackService.deleteTrack(currentTrack.getTrackId()));

            newTracks.stream()
                    .filter(newTrack -> !currentTracks.contains(newTrack))
                    .peek(newTrack -> newTrack.setTrackAlbum(curAlbum))
                    .forEach(newTrack -> trackService.createTrack(newTrack, null));

            return albumRepository.save(curAlbum);
        }
        return null;
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
}
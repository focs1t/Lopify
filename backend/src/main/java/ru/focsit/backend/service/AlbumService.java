package ru.focsit.backend.service;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.repository.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private TrackService trackService;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long albumId) {
        return albumRepository.findById(albumId);
    }

    public Album createAlbum(Album album) {
        Album savedAlbum = albumRepository.save(album);
        if (album.getTracks() != null) {
            album.getTracks().stream()
                    .peek(track -> track.setTrackAlbum(savedAlbum))
                    .forEach(trackService::createTrack);
        }
        return savedAlbum;
    }

    public Album updateAlbum(Long albumId, Album albumDetails) {
        Optional<Album> album = albumRepository.findById(albumId);
        if (album.isPresent()) {
            Album curAlbum = album.get();
            curAlbum.setAlbumName(albumDetails.getAlbumName());
            curAlbum.setAlbumDescription(albumDetails.getAlbumDescription());
            curAlbum.setAlbumImageUrl(albumDetails.getAlbumImageUrl());
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
                    .forEach(trackService::createTrack);

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
}
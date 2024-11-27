package ru.focsit.backend.services;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.repositories.AlbumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Optional<Album> getAlbumById(Long albumId) {
        return albumRepository.findById(albumId);
    }

    public Album createAlbum(Album album) {
        return albumRepository.save(album);
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
            return albumRepository.save(curAlbum);
        }
        return null;
    }

    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }
}
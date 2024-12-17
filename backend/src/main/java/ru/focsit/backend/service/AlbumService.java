package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.repository.AlbumRepository;
import ru.focsit.backend.repository.ArtistRepository;

import java.util.List;

@Service
public class AlbumService {

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public List<Album> getAllAlbums() {
        return albumRepository.findAll();
    }

    public Album getAlbumById(Long albumId) {
        return albumRepository.findById(albumId).orElseThrow(() -> new IllegalArgumentException("Album not found"));
    }

    public Album createAlbum(String name, Long artistId) {
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        Album album = new Album();
        album.setName(name);
        album.setArtist(artist);

        return albumRepository.save(album);
    }

    public Album updateAlbum(Long albumId, String name, Long artistId) {
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new IllegalArgumentException("Album not found"));
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        album.setName(name);
        album.setArtist(artist);

        return albumRepository.save(album);
    }

    public void deleteAlbum(Long albumId) {
        albumRepository.deleteById(albumId);
    }
}
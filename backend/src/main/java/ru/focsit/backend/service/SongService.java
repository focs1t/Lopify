package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.repository.SongRepository;
import ru.focsit.backend.repository.AlbumRepository;
import ru.focsit.backend.repository.ArtistRepository;

import java.util.List;

@Service
public class SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private ArtistRepository artistRepository;

    public Song getSongById(Long songId) {
        return songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
    }

    public List<Song> getSongsByAlbum(Long albumId) {
        return songRepository.findByAlbumId(albumId);
    }

    public List<Song> getSongsByArtist(Long artistId) {
        return songRepository.findByArtistId(artistId);
    }

    public Song createSong(String title, Long albumId, Long artistId) {
        // Получаем альбом и артиста через их репозитории
        Album album = albumRepository.findById(albumId).orElseThrow(() -> new IllegalArgumentException("Album not found"));
        Artist artist = artistRepository.findById(artistId).orElseThrow(() -> new IllegalArgumentException("Artist not found"));

        Song song = new Song();
        song.setTitle(title);
        song.setAlbum(album);  // Устанавливаем альбом
        song.setArtist(artist);  // Устанавливаем артиста

        return songRepository.save(song);
    }

    public void deleteSong(Long songId) {
        songRepository.deleteById(songId);
    }
}
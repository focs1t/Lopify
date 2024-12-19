package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.repository.PlaylistRepository;
import ru.focsit.backend.repository.SongRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SongService {
    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    public SongDto toDto(Song song) {
        return new SongDto(
                song.getId(),
                song.getName(),
                song.getGenre(),
                song.getArtist(),
                song.getAlbum(),
                song.getDuration()
        );
    }

    public Song fromDto(SongDto songDto) {
        return Song.builder()
                .id(songDto.getId())
                .name(songDto.getName())
                .genre(songDto.getGenre())
                .artist(songDto.getArtist())
                .album(songDto.getAlbum())
                .duration(songDto.getDuration())
                .build();
    }

    public Song createSong(SongDto songDto) {
        Song song = fromDto(songDto);
        return songRepository.save(song);
    }

    public List<SongDto> getAllSongs() {
        return songRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Song getSongById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new RuntimeException("Song not found"));
    }

    public Song updateSong(Long id, Song updatedSong) {
        Song song = getSongById(id);
        List.of(
                (Runnable) () -> song.setName(updatedSong.getName()),
                () -> song.setDuration(updatedSong.getDuration()),
                () -> song.setGenre(updatedSong.getGenre()),
                () -> song.setArtist(updatedSong.getArtist()),
                () -> song.setAlbum(updatedSong.getAlbum())
        ).forEach(Runnable::run);
        return songRepository.save(song);
    }

    public void deleteSong(Long id) {
        Song song = getSongById(id);  // Получаем объект песни по ID
        playlistRepository.removeSongFromAllPlaylists(song);  // Передаем сам объект песни

        // Затем удаление песни
        songRepository.deleteById(id);
    }

    public List<Song> getSongsByCriteria(String album, String artist, String name) {
        return songRepository.findAll().stream()
                .filter(song -> album == null || song.getAlbum().equalsIgnoreCase(album))
                .filter(song -> artist == null || song.getArtist().equalsIgnoreCase(artist))
                .filter(song -> name == null || song.getName().equalsIgnoreCase(name))
                .collect(Collectors.toList());
    }
}
package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.PlaylistRepository;
import ru.focsit.backend.repository.SongRepository;
import ru.focsit.backend.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlaylistService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private SongService songService;

    @Autowired
    private PlaylistRepository playlistRepository;

    public Playlist createPlaylist(Playlist playlist) {
        return playlistRepository.save(playlist);
    }

    // Метод для получения текущего аутентифицированного пользователя
    private User getCurrentUser() {
        String username = ((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername();
        return userRepository.findByUsername(username);
    }

    // Метод для добавления трека в плейлист
    public String addSongToPlaylist(Long songId) {
        User user = getCurrentUser();
        Optional<Song> songOptional = songRepository.findById(songId);

        if (songOptional.isEmpty()) {
            return "Song not found";
        }

        Song song = songOptional.get();
        Playlist playlist = user.getPlaylist();

        // Добавляем песню в плейлист, если её там еще нет
        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistRepository.save(playlist);
            return "Song added to playlist";
        }

        return "Song already in playlist";
    }

    // Метод для удаления трека из плейлиста
    public String removeSongFromPlaylist(Long songId) {
        User user = getCurrentUser(); // Получаем текущего пользователя
        Optional<Song> songOptional = songRepository.findById(songId);

        if (songOptional.isEmpty()) {
            return "Song not found";
        }

        Song song = songOptional.get();
        Playlist playlist = user.getPlaylist();

        // Удаляем песню из плейлиста
        if (playlist.getSongs().contains(song)) {
            playlist.getSongs().remove(song);
            playlistRepository.save(playlist);
            return "Song removed from playlist";
        }

        return "Song not found in playlist";
    }

    public List<SongDto> getSongsFromPlaylist() {
        User user = getCurrentUser(); // Получаем текущего пользователя
        if (user == null) {
            throw new IllegalStateException("Текущий пользователь не найден");
        }

        Long userId = user.getId();
        Playlist playlist = getPlaylistByUserId(userId);

        if (playlist == null) {
            throw new IllegalStateException("Плейлист для пользователя с ID " + userId + " не найден");
        }

        // Преобразуем список песен в SongDto
        return playlist.getSongs().stream()
                .map(songService::toDto) // Используем метод toDto для преобразования
                .collect(Collectors.toList());
    }

    public List<SongDto> getSongsFromPlaylistByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("ID пользователя не может быть null");
        }

        Playlist playlist = getPlaylistByUserId(userId);

        if (playlist == null) {
            throw new IllegalStateException("Плейлист для пользователя с ID " + userId + " не найден");
        }

        // Преобразуем список песен в SongDto
        return playlist.getSongs().stream()
                .map(songService::toDto) // Используем метод toDto для преобразования
                .collect(Collectors.toList());
    }

    public Playlist getPlaylistByUserId(Long userId) {
        Playlist playlist = playlistRepository.findByUserId(userId);
        if (playlist == null) {
            throw new IllegalStateException("Плейлист для пользователя с ID " + userId + " не найден");
        }
        return playlist;
    }

}
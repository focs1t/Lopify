package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;

import java.util.Set;

@Service
public class UserFavoritesService {
    @Autowired
    private UserService userService;

    @Autowired
    private SongService songService;

    public Set<Song> getUserFavorites(Long userId) {
        User user = userService.getUserById(userId);
        return user.getFavoriteSongs();
    }

    public void addSongToFavorites(Long userId, Long songId) {
        User user = userService.getUserById(userId);
        Song song = songService.getSongById(songId);
        user.getFavoriteSongs().add(song);
        userService.updateUser(userId, user);
    }

    public void removeSongFromFavorites(Long userId, Long songId) {
        User user = userService.getUserById(userId);
        Song song = songService.getSongById(songId);
        user.getFavoriteSongs().remove(song);
        userService.updateUser(userId, user);
    }
}
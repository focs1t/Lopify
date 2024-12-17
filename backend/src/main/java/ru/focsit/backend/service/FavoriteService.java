package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Favorite;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.FavoriteRepository;
import ru.focsit.backend.repository.SongRepository;
import ru.focsit.backend.repository.UserRepository;

import java.util.List;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public Favorite addToFavorites(Long userId, Long songId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));
        Song song = songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));

        // Проверяем, не добавлена ли уже эта песня в избранное
        if (favoriteRepository.findByUserIdAndSongId(userId, songId).isPresent()) {
            throw new IllegalArgumentException("Song is already in favorites");
        }

        Favorite favorite = new Favorite();
        favorite.setUser(user);
        favorite.setSong(song);

        return favoriteRepository.save(favorite);
    }

    public void removeFromFavorites(Long userId, Long songId) {
        Favorite favorite = favoriteRepository.findByUserIdAndSongId(userId, songId)
                .orElseThrow(() -> new IllegalArgumentException("Favorite not found"));
        favoriteRepository.delete(favorite);
    }
}
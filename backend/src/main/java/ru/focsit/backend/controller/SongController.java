package ru.focsit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.service.SongService;
import ru.focsit.backend.service.FavoriteService;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.RatingService;

import java.util.List;

@RestController
@RequestMapping("/api/songs")
public class SongController {

    @Autowired
    private SongService songService;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private RatingService ratingService;

    // TODO все роли
    // Получить все песни
    @GetMapping
    public List<Song> getAllSongs() {
        return songService.getAllSongs();
    }

    // TODO все роли
    // Получить песню по ID
    @GetMapping("/{id}")
    public Song getSongById(@PathVariable Long id) {
        // TODO Добавить комментарий к песне пользователи
        // TODO Получить все комментарии к песне все роли
        // TODO Оценить песню пользователи
        // TODO Получить рейтинг песни все роли
        // TODO удаление комментариев модератором
        return songService.getSongById(id);
    }

    // TODO пользователи
    // Добавить песню в избранное
    @PostMapping("/{userId}/{songId}/favorite")
    public Favorite addToFavorites(@PathVariable Long userId, @PathVariable Long songId) {
        return favoriteService.addToFavorites(userId, songId);
    }

    // TODO пользователи
    // Удалить песню из избранного
    @DeleteMapping("/{userId}/{songId}/favorite")
    public void removeFromFavorites(@PathVariable Long userId, @PathVariable Long songId) {
        favoriteService.removeFromFavorites(userId, songId);
    }
}
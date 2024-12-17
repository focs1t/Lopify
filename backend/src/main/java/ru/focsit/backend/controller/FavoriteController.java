package ru.focsit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Favorite;
import ru.focsit.backend.service.FavoriteService;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    // TODO Получить детали избранного по ID

    // TODO пользователи
    // Добавить песню в избранное
    @PostMapping("/{userId}/{songId}")
    public Favorite addToFavorites(@PathVariable Long userId, @PathVariable Long songId) {
        return favoriteService.addToFavorites(userId, songId);
    }

    // TODO пользователи
    // Удалить песню из избранного
    @DeleteMapping("/{userId}/{songId}")
    public void removeFromFavorites(@PathVariable Long userId, @PathVariable Long songId) {
        favoriteService.removeFromFavorites(userId, songId);
    }

    // TODO все роли
    // Получить избранные песни пользователя
    @GetMapping("/user/{userId}")
    public List<Favorite> getFavoritesByUser(@PathVariable Long userId) {
        return favoriteService.getFavoritesByUser(userId);
    }
}
package ru.focsit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    // TODO все роли
    // Получить все альбомы
    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    // TODO все роли
    // Получить детали альбома по ID
    @GetMapping("/{id}")
    public Album getAlbumDetails(@PathVariable Long id) {
        // TODO Получить все песни в альбоме
        return albumService.getAlbumById(id);
    }

    // TODO создание альбомов модератором

    // TODO удаление альбомов модератором
}
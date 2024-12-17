package ru.focsit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/api/artists")
public class ArtistController {

    @Autowired
    private ArtistService artistService;

    // TODO все роли
    // Получить всех артистов
    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    // TODO все роли
    // Получить детали артиста по ID
    @GetMapping("/{id}")
    public Artist getArtistDetails(@PathVariable Long id) {
        // TODO Получить все альбомы артиста
        // TODO Получить все песни артиста
        return artistService.getArtistById(id);
    }

    // TODO создание исполнителей модератором

    // TODO удаление исполнителей модератором
}
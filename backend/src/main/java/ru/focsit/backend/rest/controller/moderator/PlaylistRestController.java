package ru.focsit.backend.rest.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.service.PlaylistService;

@RestController
@RequestMapping("/api/moderator/playlists")
public class PlaylistRestController {
    @Autowired
    private PlaylistService playlistService;

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        return playlistService.getPlaylistById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
        // TODO сделать отображение треков плейлиста
        // TODO сделать поиск треков по названию или/и по исполнителю
    }


    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        return playlistService.createPlaylist(playlist);
        // TODO создание плейлиста под стандартным именем
    }


    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlistDetails) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(id, playlistDetails);
        return updatedPlaylist != null ? ResponseEntity.ok(updatedPlaylist) : ResponseEntity.notFound().build();
        // TODO удаление/добавление треков
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    // TODO создание плейлистов по названию жанра и под стандартным именем
}

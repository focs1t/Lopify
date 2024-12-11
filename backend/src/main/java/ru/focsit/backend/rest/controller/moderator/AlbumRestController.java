package ru.focsit.backend.rest.controller.moderator;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.service.AlbumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/albums")
public class AlbumRestController {

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        // TODO сделать отображение комментариев альбома
        // TODO сделать отображение треков альбома
        // TODO сделать поиск треков по названию или/и по исполнителю
        // TODO создание коммента под именем модерации
    }

    @PostMapping
    public Album createAlbum(@RequestBody Album album) {
        return albumService.createAlbum(album);
        // TODO добавление новых треков при создании альбома в ручном режиме
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody Album albumDetails) {
        Album updatedAlbum = albumService.updateAlbum(id, albumDetails);
        return updatedAlbum != null ? ResponseEntity.ok(updatedAlbum) : ResponseEntity.notFound().build();
        // TODO удаление/добавление треков
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
        // TODO удаление альбомов автоматически если в нем нет треков
    }

    // TODO Поиск для альбомов по названию, по исполнителям
}
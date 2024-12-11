package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.service.AlbumService;

import java.util.List;

@RestController
@RequestMapping("/api/user/albums")
public class AlbumRestController {

    @Autowired
    private AlbumService albumService;

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return albumService.getAlbumById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        // TODO сделать отображение комментариев альбома
        // TODO сделать отображение треков альбома
        // TODO сделать поиск треков по названию или/и по исполнителю
        // TODO создание коммента под именем конкретного пользователя
    }

    // TODO Поиск для треков по названию, по исполнителям
}

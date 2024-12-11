package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.service.ArtistService;

import java.util.List;

@RestController
@RequestMapping("/api/user/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        return artistService.getArtistById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        // TODO сделать отображение альбомов
        // TODO сделать отображение треков
        // TODO сделать отображение туров и концертов
        // TODO сделать поиск треков или/и альбома
    }
}

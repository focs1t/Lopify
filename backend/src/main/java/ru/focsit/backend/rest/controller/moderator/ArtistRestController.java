package ru.focsit.backend.rest.controller.moderator;

import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

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

    @PostMapping
    public Artist createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody Artist artistDetails) {
        Artist updatedArtist = artistService.updateArtist(id, artistDetails);
        return updatedArtist != null ? ResponseEntity.ok(updatedArtist) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.noContent().build();
    }

    // TODO Поиск для исполнителей по имени
}
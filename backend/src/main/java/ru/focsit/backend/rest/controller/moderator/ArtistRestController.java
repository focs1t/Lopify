package ru.focsit.backend.rest.controller.moderator;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.AlbumService;
import ru.focsit.backend.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.service.TrackService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/artists")
public class ArtistRestController {

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TrackService trackService;

    @GetMapping
    public List<Artist> getAllArtists() {
        return artistService.getAllArtists();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Optional<Artist> artistOptional = artistService.getArtistById(id);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            List<Album> albums = albumService.getAlbumsByArtist(artist);
            List<Track> tracks = trackService.getTracksByArtist(artist);
            artist.setAlbums(albums);
            artist.setTracks(tracks);
            return ResponseEntity.ok(artist);
        } else {
            return ResponseEntity.notFound().build();
        }
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

    @GetMapping("/search")
    public List<Artist> searchArtists(@RequestParam(required = false) String query) {
        return artistService.searchArtists(query);
    }

    @GetMapping("/{id}/albums/search")
    public List<Album> searchAlbumsByArtist(@PathVariable Long id, @RequestParam(required = false) String query) {
        Optional<Artist> artistOptional = artistService.getArtistById(id);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            return albumService.searchAlbumsByArtist(artist, query);
        } else {
            throw new IllegalArgumentException("Artist not found");
        }
    }

    @GetMapping("/{id}/tracks/search")
    public List<Track> searchTracksByArtist(@PathVariable Long id, @RequestParam(required = false) String query) {
        Optional<Artist> artistOptional = artistService.getArtistById(id);
        if (artistOptional.isPresent()) {
            Artist artist = artistOptional.get();
            return trackService.searchTracksByArtist(artist, query);
        } else {
            throw new IllegalArgumentException("Artist not found");
        }
    }
}
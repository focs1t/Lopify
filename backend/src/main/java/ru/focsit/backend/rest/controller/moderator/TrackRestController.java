package ru.focsit.backend.rest.controller.moderator;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.AlbumService;
import ru.focsit.backend.service.ArtistService;
import ru.focsit.backend.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/tracks")
public class TrackRestController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private AlbumService albumService;

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        Optional<Track> trackOptional = trackService.getTrackById(id);
        if (trackOptional.isPresent()) {
            Track track = trackOptional.get();
            List<Artist> artists = artistService.getArtistsByTrack(track);
            track.setArtists(artists);
            return ResponseEntity.ok(track);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Track createTrack(@RequestBody Track track) {
        if (track.getTrackAlbum() == null) {
            Optional<Artist> artistOptional = artistService.getArtistByTrack(track);
            if (artistOptional.isPresent()) {
                Artist artist = artistOptional.get();
                Album newAlbum = new Album();
                newAlbum.setAlbumName(track.getTrackName());
                newAlbum.setAlbumArtist(artist);
                newAlbum = albumService.createAlbum(newAlbum);
                track.setTrackAlbum(newAlbum);
            } else {
                throw new IllegalArgumentException("No artist found for the track");
            }
        }
        return trackService.createTrack(track);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Track> updateTrack(@PathVariable Long id, @RequestBody Track trackDetails) {
        Track updatedTrack = trackService.updateTrack(id, trackDetails);
        return updatedTrack != null ? ResponseEntity.ok(updatedTrack) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrack(@PathVariable Long id) {
        trackService.deleteTrack(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Track> searchTracks(@RequestParam(required = false) String query) {
        return trackService.searchTracks(query);
    }
}
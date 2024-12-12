package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.ArtistService;
import ru.focsit.backend.service.TrackService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/tracks")
public class TrackUserRestController {

    @Autowired
    private TrackService trackService;

    @Autowired
    private ArtistService artistService;

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
}

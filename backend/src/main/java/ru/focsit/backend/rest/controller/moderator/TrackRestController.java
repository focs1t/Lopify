package ru.focsit.backend.rest.controller.moderator;

import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.TrackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/moderator/tracks")
public class TrackRestController {

    @Autowired
    private TrackService trackService;

    @GetMapping
    public List<Track> getAllTracks() {
        return trackService.getAllTracks();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        return trackService.getTrackById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        // TODO сделать отображение исполнителей
        // TODO сделать отображение концертов и туров исполнителя
    }

    @PostMapping
    public Track createTrack(@RequestBody Track track) {
        return trackService.createTrack(track);
        // TODO сделать создание альбома если он не указан
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

    // TODO Поиск для треков по названию, по альбому, по исполнителям
}
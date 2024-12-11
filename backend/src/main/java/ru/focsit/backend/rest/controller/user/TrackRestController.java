package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.TrackService;

import java.util.List;

@RestController
@RequestMapping("/api/user/tracks")
public class TrackRestController {

    @Autowired
    private TrackService trackService;

    @GetMapping("/{id}")
    public ResponseEntity<Track> getTrackById(@PathVariable Long id) {
        return trackService.getTrackById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
        // TODO сделать отображение исполнителей
        // TODO сделать отображение концертов и туров исполнителя
    }
}

package ru.focsit.backend.rest.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.TrackService;
import ru.focsit.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/playlists")
public class PlaylistRestController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<Playlist> getPlaylistById(@PathVariable Long id) {
        Optional<Playlist> playlistOptional = playlistService.getPlaylistById(id);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            List<Track> tracks = trackService.getTracksByPlaylist(playlist);
            playlist.setTracks(tracks);
            return ResponseEntity.ok(playlist);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Playlist createPlaylist(@RequestBody Playlist playlist) {
        playlist.setPlaylistUser(userService.getLopifyUser());
        return playlistService.createPlaylist(playlist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody Playlist playlistDetails) {
        Playlist updatedPlaylist = playlistService.updatePlaylist(id, playlistDetails);
        return updatedPlaylist != null ? ResponseEntity.ok(updatedPlaylist) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/tracks/search")
    public List<Track> searchTracks(@PathVariable Long id, @RequestParam(required = false) String query) {
        Optional<Playlist> playlistOptional = playlistService.getPlaylistById(id);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            return trackService.searchTracksByPlaylist(playlist, query);
        } else {
            throw new IllegalArgumentException("Album not found");
        }
    }
}

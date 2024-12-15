package ru.focsit.backend.rest.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.TrackService;
import ru.focsit.backend.service.UserService;

import jakarta.validation.Valid;
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
    public Playlist createPlaylist(@RequestParam("file") MultipartFile file, @RequestPart("playlist") @Valid Playlist playlist) {
        playlist.setPlaylistUser(userService.getLopifyUser());
        return playlistService.createPlaylist(playlist, file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestPart("playlist") @Valid Playlist playlistDetails) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
        if (curUser.getUserRole().getRoleName().contains("ROLE_MODERATOR")) {
            User lopifyUser = userService.getLopifyUser();
            playlistDetails.setPlaylistUser(lopifyUser);

            Optional<Playlist> playlistOptional = playlistService.getPlaylistById(id);
            if (playlistOptional.isPresent()) {
                Playlist existingPlaylist = playlistOptional.get();
                if (existingPlaylist.getPlaylistUser().equals(lopifyUser)) {
                    Playlist updatedPlaylist = playlistService.updatePlaylist(id, playlistDetails, file);
                    return updatedPlaylist != null ? ResponseEntity.ok(updatedPlaylist) : ResponseEntity.notFound().build();
                } else {
                    return ResponseEntity.status(403).build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlaylist(@PathVariable Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
        if (curUser.getUserRole().getRoleName().contains("ROLE_MODERATOR")) {
            User lopifyUser = userService.getLopifyUser();
            Optional<Playlist> playlistOptional = playlistService.getPlaylistById(id);
            if (playlistOptional.isPresent()) {
                Playlist existingPlaylist = playlistOptional.get();
                if (existingPlaylist.getPlaylistUser().equals(lopifyUser)) {
                    playlistService.deletePlaylist(id);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(403).build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.status(403).build();
        }
    }

    @GetMapping("/{id}/tracks/search")
    public List<Track> searchTracks(@PathVariable Long id, @RequestParam(required = false) String query) {
        Optional<Playlist> playlistOptional = playlistService.getPlaylistById(id);
        if (playlistOptional.isPresent()) {
            Playlist playlist = playlistOptional.get();
            return trackService.searchTracksByPlaylist(playlist, query);
        } else {
            throw new IllegalArgumentException("Playlist not found");
        }
    }
}
package ru.focsit.backend.rest.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.service.AlbumService;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.TrackService;
import ru.focsit.backend.service.UserService;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/albums")
public class AlbumRestController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Album> getAllAlbums() {
        return albumService.getAllAlbums();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            List<Comment> comments = commentService.getCommentsByAlbum(album);
            List<Track> tracks = trackService.getTracksByAlbum(album);
            album.setComments(comments);
            album.setTracks(tracks);
            return ResponseEntity.ok(album);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public Album createAlbum(@RequestParam("file") MultipartFile file, @RequestPart("album") @Valid Album album) {
        return albumService.createAlbum(album, file);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestParam("file") MultipartFile file, @RequestPart("album") @Valid Album albumDetails) {
        Album updatedAlbum = albumService.updateAlbum(id, albumDetails, file);
        return updatedAlbum != null ? ResponseEntity.ok(updatedAlbum) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<Album> searchAlbums(@RequestParam(required = false) String query) {
        return albumService.searchAlbums(query);
    }

    @PostMapping("/{id}/comments")
    public Comment createComment(@PathVariable Long id, @RequestBody @Valid Comment comment) {
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            comment.setCommentAlbum(album);
            comment.setCommentUser(userService.getLopifyUser());
            return commentService.createComment(comment);
        } else {
            throw new IllegalArgumentException("Album not found");
        }
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        if (albumOptional.isPresent()) {
            commentService.deleteComment(commentId);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/tracks/search")
    public List<Track> searchTracks(@PathVariable Long id, @RequestParam(required = false) String query) {
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            return trackService.searchTracksByAlbum(album, query);
        } else {
            throw new IllegalArgumentException("Album not found");
        }
    }
}
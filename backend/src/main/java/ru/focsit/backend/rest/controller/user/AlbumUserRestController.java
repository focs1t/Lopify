package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.AlbumService;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.TrackService;
import ru.focsit.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user/albums")
public class AlbumUserRestController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserService userService;

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

    @GetMapping("/search")
    public List<Album> searchAlbums(@RequestParam(required = false) String query) {
        return albumService.searchAlbums(query);
    }

    @PostMapping("/{id}/comments")
    public Comment createComment(@PathVariable Long id, @RequestBody Comment comment) {
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        if (albumOptional.isPresent()) {
            Album album = albumOptional.get();
            comment.setCommentAlbum(album);
            User curUser = userService.getCurrentUser();
            comment.setCommentUser(curUser);
            return commentService.createComment(comment);
        } else {
            throw new IllegalArgumentException("Album not found");
        }
    }

    @DeleteMapping("/{id}/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long id, @PathVariable Long commentId) {
        Optional<Album> albumOptional = albumService.getAlbumById(id);
        if (albumOptional.isPresent()) {
            Optional<Comment> commentOptional = commentService.getCommentById(commentId);
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                User curUser = userService.getCurrentUser();
                if (comment.getCommentUser().equals(curUser)) {
                    commentService.deleteComment(commentId);
                    return ResponseEntity.noContent().build();
                } else {
                    return ResponseEntity.status(403).build();
                }
            } else {
                return ResponseEntity.notFound().build();
            }
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

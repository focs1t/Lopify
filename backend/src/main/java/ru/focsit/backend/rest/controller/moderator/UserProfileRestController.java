package ru.focsit.backend.rest.controller.moderator;

import ru.focsit.backend.pojo.*;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/moderator/users")
public class UserProfileRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private PlaylistService playlistService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> userOptional = userService.getUserById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            List<Comment> comments = commentService.getCommentsByUser(user);
            List<Playlist> playlists = playlistService.getPlaylistsByUser(user);
            user.setComments(comments);
            user.setPlaylists(playlists);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public List<User> searchUsers(@RequestParam(required = false) String query) {
        return userService.searchUsers(query);
    }
}
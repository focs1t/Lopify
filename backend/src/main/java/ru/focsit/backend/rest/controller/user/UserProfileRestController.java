package ru.focsit.backend.rest.controller.user;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.CommentDto;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.dto.UserDto;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.service.AuthenticationService;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/users")
@RequiredArgsConstructor
public class UserProfileRestController {
    private final UserService userService;
    private final CommentService commentService;
    private final PlaylistService playlistService;
    private final AuthenticationService authenticationService;

    @GetMapping("/me")
    public ResponseEntity<UserDto> getCurrentUser() {
        return ResponseEntity.ok(userService.getCurrentUser());
    }

    @GetMapping("/me/comments")
    public ResponseEntity<List<CommentDto>> getMyComments() {
        Long currentUserId = userService.getCurrentUser().getId();
        List<CommentDto> comments = commentService.getCommentsByUserId(currentUserId)
                .stream()
                .map(commentService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @GetMapping("/me/favorites")
    public ResponseEntity<List<SongDto>> getMyFavorites() {
        List<SongDto> songs = playlistService.getSongsFromPlaylist();
        return ResponseEntity.ok(songs);
    }

    @DeleteMapping("/me/favorites/{songId}")
    public ResponseEntity<String> removeSongFromFavorites(@PathVariable Long songId) {
        String result = playlistService.removeSongFromPlaylist(songId);

        if (result.equals("Song removed from playlist")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (result.equals("Song not found in playlist")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }
}
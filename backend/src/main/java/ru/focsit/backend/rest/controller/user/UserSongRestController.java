package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.CommentDto;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.dto.UserDto;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.SongService;
import ru.focsit.backend.service.UserFavoritesService;
import ru.focsit.backend.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/songs")
public class UserSongRestController {

    @Autowired
    private SongService songService;

    @Autowired
    private UserFavoritesService userFavoritesService;

    @Autowired
    private UserService userService;

    @Autowired
    private CommentService commentService;

    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs() {
        List<SongDto> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SongDto>> getSongs(
            @RequestParam(required = false) String album,
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String name) {

        List<Song> songs = songService.getSongsByCriteria(album, artist, name);

        List<SongDto> songDtos = songs.stream()
                .map(songService::toDto)
                .collect(Collectors.toList());

        return ResponseEntity.ok(songDtos);
    }

    @PostMapping("/favorites/{songId}")
    public ResponseEntity<Void> addSongToFavorites(@PathVariable Long songId) {
        UserDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.getId();
        userFavoritesService.addSongToFavorites(userId, songId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/favorites/{songId}")
    public ResponseEntity<Void> removeSongFromFavorites(@PathVariable Long songId) {
        UserDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.getId();
        userFavoritesService.removeSongFromFavorites(userId, songId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/favorites")
    public ResponseEntity<List<SongDto>> getUserFavorites() {
        UserDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.getId();
        Set<Song> favoriteSongs = userFavoritesService.getUserFavorites(userId);
        List<SongDto> songDtos = favoriteSongs.stream()
                .map(songService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(songDtos);
    }

    @PostMapping("/{songId}/comments")
    public ResponseEntity<CommentDto> addCommentToSong(@PathVariable Long songId, @RequestBody String content) {
        UserDto currentUser = userService.getCurrentUser();
        Long userId = currentUser.getId();
        Song song = songService.getSongById(songId); // Assuming you have a method to fetch the song by ID
        User user = userService.getUserById(userId); // Assuming you have a method to fetch the user by ID

        Comment comment = new Comment();
        comment.setContent(content);
        comment.setUser(user);
        comment.setSong(song);

        Comment savedComment = commentService.addComment(comment);
        CommentDto commentDto = commentService.toDto(savedComment);
        return ResponseEntity.ok(commentDto);
    }

    @GetMapping("/{songId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentsForSong(@PathVariable Long songId) {
        List<Comment> comments = commentService.getCommentsBySongId(songId);
        List<CommentDto> commentDtos = comments.stream()
                .map(commentService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(commentDtos);
    }
}


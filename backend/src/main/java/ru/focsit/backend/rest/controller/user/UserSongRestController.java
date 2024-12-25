package ru.focsit.backend.rest.controller.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.CommentDto;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.dto.UserDto;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.UserRepository;
import ru.focsit.backend.service.*;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user/songs")
public class UserSongRestController {

    @Autowired
    private SongService songService;

    @Autowired
    private UserService userService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private UserRepository userRepository;

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
    public ResponseEntity<String> addSongToFavorites(@PathVariable Long songId) {
        String result = playlistService.addSongToPlaylist(songId);

        if (result.equals("Song added to playlist")) {
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } else if (result.equals("Song already in playlist")) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(result);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(result);
        }
    }

    @DeleteMapping("/favorites/{songId}")
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

    @GetMapping("/favorites")
    public ResponseEntity<List<SongDto>> getUserFavorites() {
        List<SongDto> songs = playlistService.getSongsFromPlaylist();
        return ResponseEntity.ok(songs);
    }

    @PostMapping("/{songId}/comments")
    public ResponseEntity<CommentDto> addCommentToSong(@PathVariable Long songId, @RequestBody String content) {
        try {
            UserDto currentUser = userService.getCurrentUser();
            Long userId = currentUser.getId();

            Song song = songService.getSongById(songId);
            if (song == null) {
                return ResponseEntity.status(404).body(null);
            }

            User user = userService.getUserById(userId);
            if (user == null) {
                return ResponseEntity.status(404).body(null);
            }

            Comment comment = new Comment();
            comment.setContent(content);
            comment.setUser(user);
            comment.setSong(song);

            Comment savedComment = commentService.addComment(comment);
            CommentDto commentDto = commentService.toDto(savedComment);

            List<User> moderators = userRepository.findByRole(Role.ROLE_MODERATOR);
            for (User moderator : moderators) {
                String toEmail = moderator.getEmail();
                if (toEmail != null && !toEmail.isEmpty()) {
                    try {
                        String subject = "Новый комментарий к песне ";
                        String message = "Пользователь " + user.getUsername() + " добавил новый комментарий: \n" + content +
                                "\nПесня: " + song.getName() + " - " + song.getArtist();
                        emailService.sendCommentNotification(toEmail, subject, message);
                    } catch (Exception e) {
                        System.out.println("Error sending email to " + toEmail + ": " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }

            return ResponseEntity.ok(commentDto);

        } catch (Exception e) {
            System.out.println("Error while adding comment: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
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


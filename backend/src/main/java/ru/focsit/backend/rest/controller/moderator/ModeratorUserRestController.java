package ru.focsit.backend.rest.controller.moderator;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.CommentDto;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.dto.UserDto;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.CommentService;
import ru.focsit.backend.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/moderator/users")
@RequiredArgsConstructor
public class ModeratorUserRestController {
    private final UserService userService;
    private final CommentService commentService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);
        return ResponseEntity.ok(userService.toDto(user));
    }

    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<SongDto>> getUserFavorites(@PathVariable Long userId) {
        List<SongDto> favoriteSongs = userService.getFavoriteSongsByUserId(userId);
        return ResponseEntity.ok(favoriteSongs);
    }

    @GetMapping("{userId}/comments")
    public ResponseEntity<List<CommentDto>> getUserComments(@PathVariable Long userId) {
        List<CommentDto> comments = commentService.getCommentsByUserId(userId)
                .stream()
                .map(commentService::toDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(comments);
    }

    @DeleteMapping("/{userId}/comments/{commentId}")
    public ResponseEntity<Void> deleteUserComment(@PathVariable Long userId, @PathVariable Long commentId) {
        // Проверяем, принадлежит ли комментарий пользователю
        boolean commentBelongsToUser = commentService.commentBelongsToUser(commentId, userId);
        if (!commentBelongsToUser) {
            return ResponseEntity.notFound().build();
        }

        // Удаляем комментарий
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }
}

package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.dto.CommentDto;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.CommentRepository;

import java.util.List;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    public CommentDto toDto(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getContent(),
                comment.getUser().getId(),
                comment.getUser().getUsername(),
                comment.getSong().getId(),
                comment.getSong().getName()
        );
    }

    public Comment fromDto(CommentDto commentDto, User user, Song song) {
        return Comment.builder()
                .id(commentDto.getId())
                .content(commentDto.getContent())
                .user(user)
                .song(song)
                .build();
    }

    public List<Comment> getCommentsBySongId(Long songId) {
        return commentRepository.findBySongId(songId);
    }

    public List<Comment> getCommentsByUserId(Long userId) {
        return commentRepository.findByUserId(userId);
    }

    public Comment addComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
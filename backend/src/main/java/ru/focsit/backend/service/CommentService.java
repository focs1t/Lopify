package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.CommentRepository;
import ru.focsit.backend.repository.UserRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment createComment(@Valid Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, @Valid Comment commentDetails) {
        Optional<Comment> comment = commentRepository.findById(commentId);
        if (comment.isPresent()) {
            Comment curComment = comment.get();
            curComment.setCommentText(commentDetails.getCommentText());
            curComment.setCommentDate(commentDetails.getCommentDate());
            curComment.setCommentUser(commentDetails.getCommentUser());
            curComment.setCommentAlbum(commentDetails.getCommentAlbum());
            return commentRepository.save(curComment);
        }
        return null;
    }

    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    public List<Comment> getCommentsByAlbum(Album album) {
        return commentRepository.findByCommentAlbum(album);
    }

    public List<Comment> getCommentsByUser(User user) {
        return getAllComments().stream()
                .filter(comment -> comment.getCommentUser().equals(user))
                .collect(Collectors.toList());
    }
}
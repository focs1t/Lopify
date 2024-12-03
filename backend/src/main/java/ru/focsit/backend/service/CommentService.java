package ru.focsit.backend.service;

import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public Comment updateComment(Long commentId, Comment commentDetails) {
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
}

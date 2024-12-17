package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.repository.CommentRepository;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.SongRepository;
import ru.focsit.backend.repository.UserRepository;

import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Comment> getCommentsBySong(Long songId) {
        return commentRepository.findBySongId(songId);
    }

    public Comment addComment(Long songId, Long userId, String content) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Comment comment = new Comment();
        comment.setSong(song);
        comment.setUser(user);
        comment.setContent(content);

        return commentRepository.save(comment);
    }
}
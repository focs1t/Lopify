package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.User;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByCommentUser(User user);
    List<Comment> findByCommentAlbum(Album album);
}

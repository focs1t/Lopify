package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

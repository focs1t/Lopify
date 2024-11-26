package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}

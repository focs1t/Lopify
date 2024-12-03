package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Genre;

public interface GenreRepository extends JpaRepository<Genre, Long> {
}

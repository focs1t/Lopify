package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.focsit.backend.pojo.Song;

import java.util.List;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}

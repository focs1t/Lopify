package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Album;

public interface AlbumRepository extends JpaRepository<Album, Long> {
}

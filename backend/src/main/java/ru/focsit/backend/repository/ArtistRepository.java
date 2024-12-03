package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}

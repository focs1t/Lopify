package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Artist;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}

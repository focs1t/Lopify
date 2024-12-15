package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Genre;
import ru.focsit.backend.repository.GenreRepository;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class GenreService {

    @Autowired
    private GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll();
    }

    public Optional<Genre> getGenreById(Long genreId) {
        return genreRepository.findById(genreId);
    }

    public Genre createGenre(@Valid Genre genre) {
        return genreRepository.save(genre);
    }

    public Genre updateGenre(Long genreId, @Valid Genre genreDetails) {
        Optional<Genre> genre = genreRepository.findById(genreId);
        if (genre.isPresent()) {
            Genre curGenre = genre.get();
            curGenre.setGenreName(genreDetails.getGenreName());
            return genreRepository.save(curGenre);
        }
        return null;
    }

    public void deleteGenre(Long genreId) {
        genreRepository.deleteById(genreId);
    }
}
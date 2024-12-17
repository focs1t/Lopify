package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.repository.ArtistRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ArtistService {

    private final ArtistRepository artistRepository;

    @Autowired
    public ArtistService(ArtistRepository artistRepository) {
        this.artistRepository = artistRepository;
    }

    // Получить всех исполнителей
    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    // Получить исполнителя по ID
    public Optional<Artist> getArtistById(Long artistId) {
        return artistRepository.findById(artistId);
    }

    // Добавить нового исполнителя
    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    // Обновить информацию об исполнителе
    public Artist updateArtist(Long artistId, Artist artist) {
        if (artistRepository.existsById(artistId)) {
            artist.setId(artistId);
            return artistRepository.save(artist);
        }
        return null; // Возвращаем null если исполнителя с таким ID не существует
    }

    // Удалить исполнителя
    public boolean deleteArtist(Long artistId) {
        if (artistRepository.existsById(artistId)) {
            artistRepository.deleteById(artistId);
            return true;
        }
        return false;
    }
}
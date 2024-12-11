package ru.focsit.backend.service;

import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.repository.ArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ArtistService {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TrackService trackService;

    public List<Artist> getAllArtists() {
        return artistRepository.findAll();
    }

    public Optional<Artist> getArtistById(Long artistId) {
        return artistRepository.findById(artistId);
    }

    public Artist createArtist(Artist artist) {
        return artistRepository.save(artist);
    }

    public Artist updateArtist(Long artistId, Artist artistDetails) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        if (artist.isPresent()) {
            Artist curArtist = artist.get();
            curArtist.setArtistName(artistDetails.getArtistName());
            curArtist.setArtistBio(artistDetails.getArtistBio());
            curArtist.setArtistCountry(artistDetails.getArtistCountry());
            return artistRepository.save(curArtist);
        }
        return null;
    }

    public void deleteArtist(Long artistId) {
        artistRepository.deleteById(artistId);
    }

    public List<Artist> searchArtists(String query) {
        return getAllArtists().stream()
                .filter(artist -> artist.getArtistName().equals(query))
                .collect(Collectors.toList());
    }

    public List<Artist> getArtistsByTrack(Track track) {
        return getAllArtists().stream()
                .filter(artist -> artist.getTracks().contains(track))
                .collect(Collectors.toList());
    }

    public Optional<Artist> getArtistByTrack(Track track) {
        return getAllArtists().stream()
                .filter(artist -> artist.getTracks().contains(track))
                .findFirst();
    }
}
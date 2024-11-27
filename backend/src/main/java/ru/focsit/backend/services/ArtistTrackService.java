package ru.focsit.backend.services;

import ru.focsit.backend.pojo.ArtistTrack;
import ru.focsit.backend.pojo.ArtistTrackId;
import ru.focsit.backend.repositories.ArtistTrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistTrackService {

    @Autowired
    private ArtistTrackRepository artistTrackRepository;

    public List<ArtistTrack> getAllArtistTracks() {
        return artistTrackRepository.findAll();
    }

    public ArtistTrack createArtistTrack(ArtistTrack artistTrack) {
        return artistTrackRepository.save(artistTrack);
    }

    public void deleteArtistTrack(Long artistId, Long trackId) {
        artistTrackRepository.deleteById(new ArtistTrackId(artistId, trackId));
    }
}
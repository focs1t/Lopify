package ru.focsit.backend.services;

import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.repositories.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TrackService {

    @Autowired
    private TrackRepository trackRepository;

    public List<Track> getAllTracks() {
        return trackRepository.findAll();
    }

    public Optional<Track> getTrackById(Long trackId) {
        return trackRepository.findById(trackId);
    }

    public Track createTrack(Track track) {
        return trackRepository.save(track);
    }

    public Track updateTrack(Long trackId, Track trackDetails) {
        Optional<Track> track = trackRepository.findById(trackId);
        if (track.isPresent()) {
            Track curTrack = track.get();
            curTrack.setTrackName(trackDetails.getTrackName());
            curTrack.setTrackDuration(trackDetails.getTrackDuration());
            curTrack.setTrackAlbum(trackDetails.getTrackAlbum());
            return trackRepository.save(curTrack);
        }
        return null;
    }

    public void deleteTrack(Long trackId) {
        trackRepository.deleteById(trackId);
    }
}
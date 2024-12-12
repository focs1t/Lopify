package ru.focsit.backend.service;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.repository.TrackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public List<Track> searchTracks(String query) {
        return getAllTracks().stream()
                .filter(track -> track.getTrackName().contains(query) ||
                        track.getArtists().stream().anyMatch(artist -> artist.getArtistName().contains(query)))
                .collect(Collectors.toList());
    }

    public List<Track> getTracksByAlbum(Album album) {
        return getAllTracks().stream()
                .filter(track -> track.getTrackAlbum().equals(album))
                .collect(Collectors.toList());
    }

    public List<Track> searchTracksByAlbum(Album album, String query) {
        return getTracksByAlbum(album).stream()
                .filter(track -> track.getTrackName().contains(query) ||
                        track.getArtists().stream().anyMatch(artist -> artist.getArtistName().contains(query)))
                .collect(Collectors.toList());
    }

    public List<Track> getTracksByArtist(Artist artist) {
        return getAllTracks().stream()
                .filter(track -> track.getArtists().contains(artist))
                .collect(Collectors.toList());
    }

    public List<Track> searchTracksByArtist(Artist artist, String query) {
        return getTracksByArtist(artist).stream()
                .filter(track -> track.getTrackName().contains(query))
                .collect(Collectors.toList());
    }

    public List<Track> getTracksByPlaylist(Playlist playlist) {
        return getAllTracks().stream()
                .filter(track -> track.getPlaylists().contains(playlist))
                .collect(Collectors.toList());
    }

    public List<Track> searchTracksByPlaylist(Playlist playlist, String query) {
        return getTracksByPlaylist(playlist).stream()
                .filter(track -> track.getTrackName().contains(query) ||
                        track.getArtists().stream().anyMatch(artist -> artist.getArtistName().contains(query)))
                .collect(Collectors.toList());
    }
}
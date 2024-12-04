package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.util.List;

@Controller
@RequestMapping("/moderator/tracks")
public class TrackController {

    @Autowired
    private TrackRepository trackRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @GetMapping
    public String listTracks(Model model) {
        List<Track> tracks = trackRepository.findAll();
        model.addAttribute("tracks", tracks);
        return "moderator/tracks/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("track", new Track());
        List<Artist> artists = artistRepository.findAll();
        List<Album> albums = albumRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Playlist> playlists = playlistRepository.findAll();
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("genres", genres);
        model.addAttribute("playlists", playlists);
        return "moderator/tracks/new";
    }

    @PostMapping
    public String createTrack(@ModelAttribute Track track) {
        trackRepository.save(track);
        return "redirect:/moderator/tracks";
    }

    @GetMapping("/edit/{id}")
    public String editTrack(@PathVariable Long id, Model model) {
        Track track = trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid track Id:" + id));

        List<Artist> artists = artistRepository.findAll();
        List<Album> albums = albumRepository.findAll();
        List<Genre> genres = genreRepository.findAll();
        List<Playlist> playlists = playlistRepository.findAll();

        model.addAttribute("track", track);
        model.addAttribute("artists", artists);
        model.addAttribute("albums", albums);
        model.addAttribute("genres", genres);
        model.addAttribute("playlists", playlists);

        return "moderator/tracks/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTrack(@PathVariable Long id, @ModelAttribute Track track) {
        Track existingTrack = trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid track Id:" + id));
        existingTrack.setTrackName(track.getTrackName());
        existingTrack.setTrackDuration(track.getTrackDuration());
        existingTrack.setTrackDate(track.getTrackDate());
        existingTrack.setTrackImageUrl(track.getTrackImageUrl());
        existingTrack.setTrackSongUrl(track.getTrackSongUrl());
        existingTrack.setTrackAlbum(track.getTrackAlbum());
        existingTrack.setTrackGenre(track.getTrackGenre());
        existingTrack.setArtists(track.getArtists());
        existingTrack.setPlaylists(track.getPlaylists());
        trackRepository.save(existingTrack);
        return "redirect:/moderator/tracks";
    }

    @GetMapping("/delete/{id}")
    public String deleteTrack(@PathVariable Long id) {
        Track track = trackRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid track Id:" + id));
        trackRepository.delete(track);
        return "redirect:/moderator/tracks";
    }
}
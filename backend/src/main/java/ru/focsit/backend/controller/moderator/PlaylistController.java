package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.util.List;

@Controller
@RequestMapping("/moderator/playlists")
public class PlaylistController {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping
    public String listPlaylists(Model model) {
        List<Playlist> playlists = playlistRepository.findAll();
        model.addAttribute("playlists", playlists);
        return "moderator/playlists/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("playlist", new Playlist());
        List<User> users = userRepository.findAll();
        List<Track> tracks = trackRepository.findAll();
        model.addAttribute("users", users);
        model.addAttribute("tracks", tracks);
        return "moderator/playlists/new";
    }

    @GetMapping("/{id}")
    public String viewPlaylistDetails(@PathVariable Long id, Model model) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Track> tracks = trackRepository.findByPlaylists(playlist);
        model.addAttribute("playlist", playlist);
        model.addAttribute("tracks", tracks);
        return "moderator/users/details";
    }

    @PostMapping
    public String createPlaylist(@ModelAttribute Playlist playlist) {
        playlistRepository.save(playlist);
        return "redirect:/moderator/playlists";
    }

    @GetMapping("/edit/{id}")
    public String editPlaylist(@PathVariable Long id, Model model) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid playlist Id:" + id));

        List<User> users = userRepository.findAll();
        List<Track> tracks = trackRepository.findAll();

        model.addAttribute("playlist", playlist);
        model.addAttribute("users", users);
        model.addAttribute("tracks", tracks);

        return "moderator/playlists/edit";
    }

    @PostMapping("/update/{id}")
    public String updatePlaylist(@PathVariable Long id, @ModelAttribute Playlist playlist) {
        Playlist existingPlaylist = playlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid playlist Id:" + id));
        existingPlaylist.setPlaylistName(playlist.getPlaylistName());
        existingPlaylist.setPlaylistUser(playlist.getPlaylistUser());
        existingPlaylist.setPlaylistDescription(playlist.getPlaylistDescription());
        existingPlaylist.setPlaylistImageUrl(playlist.getPlaylistImageUrl());
        existingPlaylist.setTracks(playlist.getTracks());
        playlistRepository.save(existingPlaylist);
        return "redirect:/moderator/playlists";
    }

    @GetMapping("/delete/{id}")
    public String deletePlaylist(@PathVariable Long id) {
        Playlist playlist = playlistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid playlist Id:" + id));
        playlistRepository.delete(playlist);
        return "redirect:/moderator/playlists";
    }
}
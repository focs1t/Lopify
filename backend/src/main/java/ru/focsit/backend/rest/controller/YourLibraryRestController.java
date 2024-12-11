package ru.focsit.backend.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.pojo.UserRegistrationDto;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.TrackService;
import ru.focsit.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/your-library")
public class YourLibraryRestController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Logged out successfully";
    }

    @GetMapping("/my-profile")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<User> getUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
        List<Playlist> playlists = playlistService.getPlaylistsByUser(curUser);
        curUser.setPlaylists(playlists);
        return ResponseEntity.ok(curUser);
    }
    // для юзера!!!
    // TODO создание плейлиста под своим именем
    // TODO отображение своих плейлистов
    // TODO поиск своих плейлистов

    // для модера!!!
    // TODO создание плейлиста под стандартным именем(LopifyUser)
    // TODO отображение стандартных плейлистов(LopifyUser)
    // TODO поиск стандартных плейлистов(LopifyUser)

    // для админа!!!
    // TODO создание пользователя
    // TODO отображение пользователей
    // Для пользователя
    @PostMapping("/create-playlist")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Playlist> createPlaylist(@RequestBody Playlist playlist) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
        playlist.setPlaylistUser(curUser);
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        return ResponseEntity.ok(createdPlaylist);
    }

    @GetMapping("/my-playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Playlist>> getMyPlaylists() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
        List<Playlist> playlists = playlistService.getPlaylistsByUser(curUser);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/search-my-playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Playlist>> searchMyPlaylists(@RequestParam String query) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
        List<Playlist> playlists = playlistService.searchPlaylistsByUser(curUser, query);
        return ResponseEntity.ok(playlists);
    }

    // Для модератора
    @PostMapping("/create-lopify-playlist")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ResponseEntity<Playlist> createStandardPlaylist(@RequestBody Playlist playlist) {
        User lopifyUser = userService.getLopifyUser();
        playlist.setPlaylistUser(lopifyUser);
        Playlist createdPlaylist = playlistService.createPlaylist(playlist);
        return ResponseEntity.ok(createdPlaylist);
    }

    @GetMapping("/lopify-playlists")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ResponseEntity<List<Playlist>> getStandardPlaylists() {
        User lopifyUser = userService.getLopifyUser();
        List<Playlist> playlists = playlistService.getPlaylistsByUser(lopifyUser);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/search-lopify-playlists")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ResponseEntity<List<Playlist>> searchStandardPlaylists(@RequestParam String query) {
        User lopifyUser = userService.getLopifyUser();
        List<Playlist> playlists = playlistService.searchPlaylistsByUser(lopifyUser, query);
        return ResponseEntity.ok(playlists);
    }

    // Для админа
    @PostMapping("/create-user")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody UserRegistrationDto userRegistrationDto) {
        try {
            userService.createUser(userRegistrationDto);
            return ResponseEntity.ok("User registered successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error registering user: " + e.getMessage());
        }
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}

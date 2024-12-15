package ru.focsit.backend.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.focsit.backend.dto.JwtAuthenticationResponse;
import ru.focsit.backend.dto.SignUpRequest;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.AuthenticationService;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/your-library")
@RequiredArgsConstructor
public class YourLibraryRestController {
    @Autowired
    private PlaylistService playlistService;

    private final AuthenticationService authenticationService;

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
        User curUser = userService.getCurrentUser();
        List<Playlist> playlists = playlistService.getPlaylistsByUser(curUser);
        curUser.setPlaylists(playlists);
        return ResponseEntity.ok(curUser);
    }

    // Для пользователя
    @PostMapping("/create-playlist")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Playlist> createPlaylist(@RequestParam("file") MultipartFile file, @RequestPart("playlist") @Valid Playlist playlist) {
        User curUser = userService.getCurrentUser();
        playlist.setPlaylistUser(curUser);
        Playlist createdPlaylist = playlistService.createPlaylist(playlist, file);
        return ResponseEntity.ok(createdPlaylist);
    }

    @GetMapping("/my-playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Playlist>> getMyPlaylists() {
        User curUser = userService.getCurrentUser();
        List<Playlist> playlists = playlistService.getPlaylistsByUser(curUser);
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/search-my-playlists")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Playlist>> searchMyPlaylists(@RequestParam String query) {
        User curUser = userService.getCurrentUser();
        List<Playlist> playlists = playlistService.searchPlaylistsByUser(curUser, query);
        return ResponseEntity.ok(playlists);
    }

    // Для модератора
    @PostMapping("/create-lopify-playlist")
    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    public ResponseEntity<Playlist> createStandardPlaylist(@RequestParam("file") MultipartFile file, @RequestPart("playlist") @Valid Playlist playlist) {
        User lopifyUser = userService.getLopifyUser();
        playlist.setPlaylistUser(lopifyUser);
        Playlist createdPlaylist = playlistService.createPlaylist(playlist, file);
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
    public JwtAuthenticationResponse registerUser(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.registerUser(request);
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }
}
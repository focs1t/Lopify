package ru.focsit.backend.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.service.*;

import java.util.List;

@RestController
@RequestMapping("/api/home")
public class HomeRestController {
    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private ArtistService artistService;

    @PostMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "Logged out successfully";
    }

    @GetMapping("/my-profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<User> getUserProfile() {
        User curUser = userService.getCurrentUser();
        List<Playlist> playlists = playlistService.getPlaylistsByUser(curUser);
        curUser.setPlaylists(playlists);
        return ResponseEntity.ok(curUser);
    }

    @GetMapping("/favourites")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Playlist> getLikedTracks() {
        Playlist likedTracks = playlistService.getFirstPlaylist();
        return ResponseEntity.ok(likedTracks);
    }

    public ResponseEntity<Void> redirectToCountries() {
        return ResponseEntity.status(301)
                .header("Location", "/api/admin/countries")
                .build();
    }

    public ResponseEntity<Void> redirectToGenres() {
        return ResponseEntity.status(301)
                .header("Location", "/api/admin/genres")
                .build();
    }

    public ResponseEntity<Void> redirectToRoles() {
        return ResponseEntity.status(301)
                .header("Location", "/api/admin/roles")
                .build();
    }

    public ResponseEntity<Void> redirectToUsers() {
        return ResponseEntity.status(301)
                .header("Location", "/api/admin/users")
                .build();
    }

    public ResponseEntity<Void> redirectToAlbums() {
        return ResponseEntity.status(301)
                .header("Location", "/api/moderator/albums")
                .build();
    }

    public ResponseEntity<Void> redirectToTracks() {
        return ResponseEntity.status(301)
                .header("Location", "/api/moderator/tracks")
                .build();
    }

    public ResponseEntity<Void> redirectToArtists() {
        return ResponseEntity.status(301)
                .header("Location", "/api/moderator/artists")
                .build();
    }

    public ResponseEntity<Void> redirectToPlaylists() {
        return ResponseEntity.status(301)
                .header("Location", "/api/moderator/playlists")
                .build();
    }

    public ResponseEntity<Void> redirectToComments() {
        return ResponseEntity.status(301)
                .header("Location", "/api/moderator/comments")
                .build();
    }
}
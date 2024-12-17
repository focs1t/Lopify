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
}
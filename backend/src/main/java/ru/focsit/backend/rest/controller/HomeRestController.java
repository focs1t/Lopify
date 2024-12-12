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
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String curUserName = authentication.getName();
        User curUser = userService.findByUserLogin(curUserName);
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

    // Для админа!!!
    @GetMapping("/admin/genres")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Genre>> getGenres() {
        List<Genre> genres = genreService.getAllGenres();
        return ResponseEntity.ok(genres);
    }

    @GetMapping("/admin/countries")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getAllCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping("/admin/roles")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> roles = roleService.getAllRoles();
        return ResponseEntity.ok(roles);
    }

    @GetMapping("/admin/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    // Для модера!!!
    @GetMapping("/moderator/albums")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Album>> getAlbums() {
        List<Album> albums = albumService.getAllAlbums();
        return ResponseEntity.ok(albums);
    }

    @GetMapping("/moderator/tracks")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Track>> getTracks() {
        List<Track> tracks = trackService.getAllTracks();
        return ResponseEntity.ok(tracks);
    }

    @GetMapping("/moderator/artists")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Artist>> getArtists() {
        List<Artist> artists = artistService.getAllArtists();
        return ResponseEntity.ok(artists);
    }

    @GetMapping("/moderator/playlists")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Playlist>> getPlaylists() {
        List<Playlist> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @GetMapping("/moderator/comments")
    @PreAuthorize("hasRole('MODERATOR')")
    public ResponseEntity<List<Comment>> getComments() {
        List<Comment> comments = commentService.getAllComments();
        return ResponseEntity.ok(comments);
    }
}
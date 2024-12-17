package ru.focsit.backend.rest.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.SearchResults;
import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/search-page")
public class SearchRestController {

    @Autowired
    private AlbumService albumService;

    @Autowired
    private ArtistService artistService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private TrackService trackService;

    @Autowired
    private UserService userService;

    @Autowired
    private GenreService genreService;

    @GetMapping("/search")
    public SearchResults search(@RequestParam String query) {
        List<Album> albums = albumService.searchAlbums(query);
        List<Artist> artists = artistService.searchArtists(query);
        List<Playlist> playlists = playlistService.searchPlaylists(query);
        List<Track> tracks = trackService.searchTracks(query);
        List<User> users = userService.searchUsers(query);

        return new SearchResults(albums, artists, playlists, tracks, users);
    }

    @GetMapping("/albums/by-genre")
    public ResponseEntity<Map<String, List<Album>>> getAlbumsByAllGenres() {
        Map<String, List<Album>> albumsByGenre = albumService.getAlbumsByAllGenres(genreService.getAllGenres());
        return ResponseEntity.ok(albumsByGenre);
    }
}
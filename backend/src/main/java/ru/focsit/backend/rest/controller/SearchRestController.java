package ru.focsit.backend.rest.controller;

import ru.focsit.backend.pojo.Album;
import ru.focsit.backend.pojo.Artist;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.Track;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.AlbumService;
import ru.focsit.backend.service.ArtistService;
import ru.focsit.backend.service.PlaylistService;
import ru.focsit.backend.service.TrackService;
import ru.focsit.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@RestController
public class SearchRestController {

    private final AlbumService albumService;
    private final ArtistService artistService;
    private final PlaylistService playlistService;
    private final TrackService trackService;
    private final UserService userService;

    @Autowired
    public SearchRestController(AlbumService albumService, ArtistService artistService,
                                PlaylistService playlistService, TrackService trackService,
                                UserService userService) {
        this.albumService = albumService;
        this.artistService = artistService;
        this.playlistService = playlistService;
        this.trackService = trackService;
        this.userService = userService;
    }

    @GetMapping("/api/search")
    public SearchResults search(@RequestParam String query) {
        List<Album> albums = albumService.searchAlbums(query);
        List<Artist> artists = artistService.searchArtists(query);
        List<Playlist> playlists = playlistService.searchPlaylists(query);
        List<Track> tracks = trackService.searchTracks(query);
        List<User> users = userService.searchUsers(query);

        return new SearchResults(albums, artists, playlists, tracks, users);
    }

    @Data
    @AllArgsConstructor
    public static class SearchResults {
        private List<Album> albums;
        private List<Artist> artists;
        private List<Playlist> playlists;
        private List<Track> tracks;
        private List<User> users;
    }
}
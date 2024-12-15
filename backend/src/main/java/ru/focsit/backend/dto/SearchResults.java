package ru.focsit.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.focsit.backend.pojo.*;

import java.util.List;

@Data
@AllArgsConstructor
public class SearchResults {
    private List<Album> albums;
    private List<Artist> artists;
    private List<Playlist> playlists;
    private List<Track> tracks;
    private List<User> users;
}

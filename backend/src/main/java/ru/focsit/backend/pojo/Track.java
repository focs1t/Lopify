package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "tracks")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trackId")
    private Long trackId;

    @Column(name = "trackName", nullable = false)
    private String trackName;

    @Column(name = "trackDate")
    private LocalDate trackDate;

    @Column(name = "trackImageUrl", nullable = false)
    private String trackImageUrl;

    @Column(name = "trackSongUrl", nullable = false)
    private String trackSongUrl;

    @Column(name = "trackDuration")
    private LocalTime trackDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackAlbumId")
    private Album trackAlbum;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackGenreId")
    private Genre trackGenre;

    @ManyToMany
    @JoinTable(
            name = "tracksArtists",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "artistId")
    )
    private List<Artist> artists;

    @ManyToMany
    @JoinTable(
            name = "tracksPlaylists",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "playlistId")
    )
    private List<Playlist> playlists;
}


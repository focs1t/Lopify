package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

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

    @NotBlank(message = "Название трека обязательно")
    @Size(max = 100, message = "Название трека должно быть меньше 100 символов")
    @Column(name = "trackName", nullable = false)
    private String trackName;

    @Column(name = "trackDate")
    private LocalDate trackDate;

    @NotBlank(message = "URL изображения трека обязателен")
    @Size(max = 255, message = "URL изображения трека должен быть меньше 255 символов")
    @Column(name = "trackImageUrl", nullable = false)
    private String trackImageUrl;

    @NotBlank(message = "URL песни трека обязателен")
    @Size(max = 255, message = "URL песни трека должен быть меньше 255 символов")
    @Column(name = "trackSongUrl", nullable = false)
    private String trackSongUrl;

    @Column(name = "trackDuration")
    private LocalTime trackDuration;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackAlbumId")
    @JsonIdentityReference
    private Album trackAlbum;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "trackGenreId")
    @JsonIdentityReference
    private Genre trackGenre;

    @ManyToMany
    @JoinTable(
            name = "tracksArtists",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "artistId")
    )
    @JsonManagedReference
    private List<Artist> artists;

    @ManyToMany
    @JoinTable(
            name = "tracksPlaylists",
            joinColumns = @JoinColumn(name = "trackId"),
            inverseJoinColumns = @JoinColumn(name = "playlistId")
    )
    @JsonBackReference
    private List<Playlist> playlists;
}
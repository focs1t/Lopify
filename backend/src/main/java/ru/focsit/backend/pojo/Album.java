package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "albums")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "albumId")
    private Long albumId;

    @NotBlank(message = "Название альбома обязательно")
    @Size(max = 100, message = "Название альбома должно быть меньше 100 символов")
    @Column(name = "albumName", nullable = false)
    private String albumName;

    @NotBlank(message = "Описание альбома обязательно")
    @Size(max = 500, message = "Описание альбома должно быть меньше 500 символов")
    @Column(name = "albumDescription", nullable = false)
    private String albumDescription;

    @NotBlank(message = "URL изображения альбома обязателен")
    @Size(max = 255, message = "URL изображения альбома должен быть меньше 255 символов")
    @Column(name = "albumImageUrl", nullable = false)
    private String albumImageUrl;

    @Column(name = "albumReleaseDate")
    private LocalDate albumReleaseDate;

    @Column(name = "albumDuration")
    private LocalTime albumDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albumArtistId")
    @JsonBackReference
    private Artist albumArtist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albumGenreId")
    private Genre albumGenre;

    @OneToMany(mappedBy = "commentAlbum", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "trackAlbum", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Track> tracks;
}
package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

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
    private String albumImageUrl; // TODO берем фото по треку

    @Column(name = "albumReleaseDate")
    private LocalDate albumReleaseDate = LocalDate.now();

    @Column(name = "albumDuration")
    private LocalTime albumDuration;  // TODO изменить на суммму по трекам

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "albumArtistId")
    @JsonIdentityReference
    private Artist albumArtist;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "albumGenreId")
    @JsonIdentityReference
    private Genre albumGenre;

    @OneToMany(mappedBy = "commentAlbum", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Comment> comments;

    @OneToMany(mappedBy = "trackAlbum", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Track> tracks;
}
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

import java.time.LocalTime;
import java.util.List;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlistId")
    private Long playlistId;

    @NotBlank(message = "Название плейлиста обязательно")
    @Size(max = 100, message = "Название плейлиста должно быть меньше 100 символов")
    @Column(name = "playlistName", nullable = false)
    private String playlistName;

    @NotBlank(message = "Описание плейлиста обязательно")
    @Size(max = 500, message = "Описание плейлиста должно быть меньше 500 символов")
    @Column(name = "playlistDescription", nullable = false)
    private String playlistDescription;

    @Column(name = "playlistDuration")
    private LocalTime playlistDuration;  // TODO изменить на суммму по трекам

    @NotBlank(message = "URL изображения плейлиста обязателен")
    @Size(max = 255, message = "URL изображения плейлиста должен быть меньше 255 символов")
    @Column(name = "playlistImageUrl", nullable = false)
    private String playlistImageUrl;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "playlistUserId")
    @JsonIdentityReference
    private User playlistUser;

    @ManyToMany(mappedBy = "playlists")
    @JsonBackReference
    private List<Track> tracks;
}
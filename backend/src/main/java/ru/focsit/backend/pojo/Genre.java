package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genreId")
    private Long genreId;

    @NotBlank(message = "Название жанра обязательно")
    @Size(max = 100, message = "Название жанра должно быть меньше 100 символов")
    @Column(name = "genreName", nullable = false, unique = true)
    private String genreName;

    @OneToMany(mappedBy = "trackGenre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Track> tracks;

    @OneToMany(mappedBy = "albumGenre", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Album> albums;

    @PreRemove
    @PreUpdate
    private void checkProtectedGenre() {
        List<String> protectedGenres = List.of(
                "Pop", "Rock", "Hip-Hop", "Jazz", "Classical", "Electronic", "Reggae", "Country",
                "Metal", "Blues", "Folk", "Soul", "Alternative", "Indie", "K-Pop", "Trap", "R&B",
                "Dancehall", "Lo-fi", "Dubstep", "Synthwave", "Tropical House"
        );
        if (protectedGenres.contains(genreName)) {
            throw new RuntimeException("Cannot delete or update protected genre");
        }
    }
}
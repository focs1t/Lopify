package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "artists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "artistId")
    private Long artistId;

    @NotBlank(message = "Имя исполнителя обязательно")
    @Size(max = 100, message = "Имя исполнителя должно быть меньше 100 символов")
    @Column(name = "artistName", nullable = false, unique = true)
    private String artistName;

    @Size(max = 500, message = "Биография исполнителя должна быть меньше 500 символов")
    @Column(name = "artistBio")
    private String artistBio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistCountryId")
    private Country artistCountry;

    @ManyToMany(mappedBy = "artists")
    @JsonBackReference
    private List<Track> tracks;

    @OneToMany(mappedBy = "albumArtist", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Album> albums;
}
package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

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

    @Column(name = "artistName", nullable = false, unique = true)
    private String artistName;

    @Column(name = "artistBio")
    private String artistBio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistCountryId")
    private Country artistCountry;

    @ManyToMany(mappedBy = "artists")
    private List<Track> tracks;

    @OneToMany(mappedBy = "albumArtist", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Album> albums;
}


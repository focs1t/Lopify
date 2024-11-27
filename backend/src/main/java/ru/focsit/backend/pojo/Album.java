package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    @Column(name = "albumName", nullable = false)
    private String albumName;

    @Column(name = "albumDescription", nullable = false)
    private String albumDescription;

    @Column(name = "albumImageUrl", nullable = false)
    private String albumImageUrl;

    @Column(name = "albumReleaseDate")
    private LocalDate albumReleaseDate;

    @Column(name = "albumDuration")
    private LocalTime albumDuration;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albumArtistId")
    private Artist albumArtist;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "albumGenreId")
    private Genre albumGenre;
}

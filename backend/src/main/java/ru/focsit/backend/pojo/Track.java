package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Track {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TRACKID;

    @Column(nullable = false)
    private String TRACKNAME;

    private LocalDate TRACKDATE;

    @Column(nullable = false)
    private String TRACKIMAGE_URL;

    @Column(nullable = false)
    private String TRACKSONG_URL;

    private LocalTime TRACK_DURATION;

    @ManyToOne
    @JoinColumn(name = "TRACK_ALBUM_ID")
    private Album ALBUM;

    @ManyToOne
    @JoinColumn(name = "TRACK_GENRE_ID")
    private Genre GENRE;
}


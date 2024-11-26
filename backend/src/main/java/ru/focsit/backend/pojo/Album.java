package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Getter
@Setter
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ALBUMID;

    @Column(nullable = false)
    private String ALBUMNAME;

    @Column(nullable = false)
    private String ALBUMDESCRIPTION;

    @Column(nullable = false)
    private String ALBUMIMAGE_URL;

    private LocalDate ALBUMRELEASE_DATE;
    private LocalTime ALBUMDURATION;

    @ManyToOne
    @JoinColumn(name = "ALBUM_ARTIST_ID")
    private Artist ARTIST;

    @ManyToOne
    @JoinColumn(name = "ALBUM_GENRE_ID")
    private Genre GENRE;
}

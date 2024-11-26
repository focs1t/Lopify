package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long CONCERTID;

    @Column(nullable = false)
    private String CONCERTPLACE;

    @Column(nullable = false)
    private String CONCERTCITY;

    @Column(nullable = false)
    private String CONCERTTICKETURL;

    private LocalDateTime CONCERTDATE;

    @ManyToOne
    @JoinColumn(name = "CONCERT_COUNTRY_ID")
    private Country COUNTRY;

    @ManyToOne
    @JoinColumn(name = "CONCERT_TOUR_ID")
    private Tour TOUR;
}


package ru.focsit.backend.pojo;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long TOURID;

    @Column(nullable = false)
    private String TOURNAME;

    @ManyToOne
    @JoinColumn(name = "TOUR_ARTIST_ID")
    private Artist ARTIST;
}


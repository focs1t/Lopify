package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ARTISTID;

    @Column(nullable = false, unique = true)
    private String ARTISTNAME;

    private String ARTISTBIO;

    @ManyToOne
    @JoinColumn(name = "ARTIST_COUNTRY_ID")
    private Country COUNTRY;
}


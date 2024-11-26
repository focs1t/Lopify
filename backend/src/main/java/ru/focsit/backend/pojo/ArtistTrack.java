package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class ArtistTrack {

    @Id
    @ManyToOne
    @JoinColumn(name = "ARTIST_ID")
    private Artist ARTIST;

    @Id
    @ManyToOne
    @JoinColumn(name = "TRACK_ID")
    private Track TRACK;
}


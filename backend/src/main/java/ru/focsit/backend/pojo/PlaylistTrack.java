package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class PlaylistTrack {

    @Id
    @ManyToOne
    @JoinColumn(name = "PLAYLIST_ID")
    private Playlist PLAYLIST;

    @Id
    @ManyToOne
    @JoinColumn(name = "TRACK_ID")
    private Track TRACK;
}


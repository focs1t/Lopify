package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "playliststracks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(PlaylistTrackId.class)
public class PlaylistTrack {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlistId")
    private Playlist playlist;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackId")
    private Track track;
}


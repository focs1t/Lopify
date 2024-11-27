package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "artiststracks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(ArtistTrackId.class)
public class ArtistTrack {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artistId")
    private Artist artist;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trackId")
    private Track track;
}


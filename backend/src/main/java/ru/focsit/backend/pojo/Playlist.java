package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "playlists")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "playlistId")
    private Long playlistId;

    @Column(name = "playlistName", nullable = false)
    private String playlistName;

    @Column(name = "playlistDescription", nullable = false)
    private String playlistDescription;

    @Column(name = "playlistImageUrl", nullable = false)
    private String playlistImageUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "playlistUserId")
    private User playlistUser;

    @ManyToMany(mappedBy = "playlists")
    private List<Track> tracks;
}


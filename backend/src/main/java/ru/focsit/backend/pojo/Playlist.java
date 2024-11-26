package ru.focsit.backend.pojo;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Playlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long PLAYLISTID;

    @Column(nullable = false)
    private String PLAYLISTNAME;

    @Column(nullable = false)
    private String PLAYLISTDESCRIPTION;

    @Column(nullable = false)
    private String PLAYLISTIMAGE_URL;

    @ManyToOne
    @JoinColumn(name = "PLAYLIST_USER_ID")
    private User USER;
}


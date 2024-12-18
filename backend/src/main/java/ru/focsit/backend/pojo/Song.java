package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "songs")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false)
    private Integer duration;

    @Column(nullable = false, length = 255)
    private String genre;

    @Column(nullable = false, length = 255)
    private String artist;

    @Column(nullable = false, length = 255)
    private String album;

    @ManyToMany(mappedBy = "songs")
    @JsonBackReference
    private Set<Playlist> playlists;  // Связь с плейлистами
}
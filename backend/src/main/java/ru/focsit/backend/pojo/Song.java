package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.*;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;
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

    @ManyToMany(mappedBy = "songs", cascade = CascadeType.PERSIST)
    @JsonBackReference
    private Set<Playlist> playlists;  // Связь с плейлистами

    @OneToMany(mappedBy = "song", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private Set<Comment> comments;

    @Override
    public int hashCode() {
        return Objects.hash(id); // Используйте уникальные поля, не ссылающиеся на другие объекты
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Song song = (Song) obj;
        return id != null && id.equals(song.id);
    }
}
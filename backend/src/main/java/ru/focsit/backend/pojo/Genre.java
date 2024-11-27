package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "genres")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "genreId")
    private Long genreId;

    @Column(name = "genreName", nullable = false, unique = true)
    private String genreName;
}


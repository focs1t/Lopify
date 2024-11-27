package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "tours")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tourId")
    private Long tourId;

    @Column(name = "tourName", nullable = false)
    private String tourName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tourArtistId")
    private Artist tourArtist;
}


package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "concerts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Concert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "concertId")
    private Long concertId;

    @Column(name = "concertPlace", nullable = false)
    private String concertPlace;

    @Column(name = "concertCity", nullable = false)
    private String concertCity;

    @Column(name = "concertTicketUrl", nullable = false)
    private String concertTicketUrl;

    @Column(name = "concertDate")
    private LocalDateTime concertDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concertCountryId")
    private Country concertCountry;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concertTourId")
    private Tour concertTour;
}


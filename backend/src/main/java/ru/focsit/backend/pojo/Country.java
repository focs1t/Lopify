package ru.focsit.backend.pojo;

import lombok.*;

import jakarta.persistence.*;

@Entity
@Table(name = "countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "countryId")
    private Long countryId;

    @Column(name = "countryName", nullable = false, unique = true)
    private String countryName;
}

package ru.focsit.backend.pojo;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

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

    @NotBlank(message = "Название страны обязательно")
    @Size(max = 100, message = "Название страны должно быть меньше 100 символов")
    @Column(name = "countryName", nullable = false, unique = true)
    private String countryName;
}
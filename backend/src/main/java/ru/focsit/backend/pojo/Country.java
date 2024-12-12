package ru.focsit.backend.pojo;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

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

    @OneToMany(mappedBy = "userCountry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<User> users;

    @OneToMany(mappedBy = "artistCountry", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonBackReference
    private List<Artist> artists;
}
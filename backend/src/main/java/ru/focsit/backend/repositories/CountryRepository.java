package ru.focsit.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}

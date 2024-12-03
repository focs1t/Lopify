package ru.focsit.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.focsit.backend.pojo.Country;

public interface CountryRepository extends JpaRepository<Country, Long> {
}

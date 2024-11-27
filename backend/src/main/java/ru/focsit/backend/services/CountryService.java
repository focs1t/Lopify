package ru.focsit.backend.services;

import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    public Optional<Country> getCountryById(Long countryId) {
        return countryRepository.findById(countryId);
    }

    public Country createCountry(Country country) {
        return countryRepository.save(country);
    }

    public Country updateCountry(Long countryId, Country countryDetails) {
        Optional<Country> country = countryRepository.findById(countryId);
        if (country.isPresent()) {
            Country curCountry = country.get();
            curCountry.setCountryName(countryDetails.getCountryName());
            return countryRepository.save(curCountry);
        }
        return null;
    }

    public void deleteCountry(Long countryId) {
        countryRepository.deleteById(countryId);
    }
}
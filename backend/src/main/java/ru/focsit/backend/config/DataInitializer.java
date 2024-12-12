package ru.focsit.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.pojo.Genre;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.CountryService;
import ru.focsit.backend.service.GenreService;
import ru.focsit.backend.service.RoleService;
import ru.focsit.backend.service.UserService;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer {

    @Autowired
    private RoleService roleService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private GenreService genreService;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initRoles();
        initCountries();
        initGenres();
        initLopifyUser();
    }

    private void initRoles() {
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN", "ROLE_MODERATOR");
        for (String roleName : roles) {
            if (roleService.getRoleByRoleName(roleName).isEmpty()) {
                Role role = new Role();
                role.setRoleName(roleName);
                roleService.createRole(role);
            }
        }
    }

    private void initCountries() {
        List<String> countries = Arrays.asList(
                "USA", "Canada", "Mexico", "Great Britain", "Germany", "France", "Italy",
                "Spain", "Japan", "China", "India", "Australia", "Brazil", "Russia", "South African Republic"
        );
        for (String countryName : countries) {
            if (countryService.getAllCountries().stream().noneMatch(c -> c.getCountryName().equals(countryName))) {
                Country country = new Country();
                country.setCountryName(countryName);
                countryService.createCountry(country);
            }
        }
    }

    private void initGenres() {
        List<String> genres = Arrays.asList(
                "Pop", "Rock", "Hip-Hop", "Jazz", "Classical", "Electronic", "Reggae", "Country",
                "Metal", "Blues", "Folk", "Soul", "Alternative", "Indie", "K-Pop", "Trap", "R&B",
                "Dancehall", "Lo-fi", "Dubstep", "Synthwave", "Tropical House"
        );
        for (String genreName : genres) {
            if (genreService.getAllGenres().stream().noneMatch(g -> g.getGenreName().equals(genreName))) {
                Genre genre = new Genre();
                genre.setGenreName(genreName);
                genreService.createGenre(genre);
            }
        }
    }

    private void initLopifyUser() {
        if (userService.getAllUsers().stream().noneMatch(u -> u.getUsername().equals("Lopify"))) {
            User lopifyUser = new User();
            lopifyUser.setUsername("Lopify");
            lopifyUser.setPassword(passwordEncoder.encode("admin"));
            lopifyUser.setUserEmail("lopify@music.com");
            lopifyUser.setUserRole(roleService.getRoleByRoleName("ROLE_ADMIN").orElseThrow());
            lopifyUser.setUserCountry(countryService.getAllCountries().stream()
                    .filter(c -> c.getCountryName().equals("USA"))
                    .findFirst()
                    .orElseThrow());
            userService.createUser(lopifyUser);
        }
    }
}
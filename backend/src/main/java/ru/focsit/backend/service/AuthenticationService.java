package ru.focsit.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.focsit.backend.dto.JwtAuthenticationResponse;
import ru.focsit.backend.dto.SignInRequest;
import ru.focsit.backend.dto.SignUpRequest;
import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserService userService;
    private final RoleService roleService;
    private final CountryService countryService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationResponse signUp(SignUpRequest request) {
        Optional<Role> role = roleService.getRoleByRoleName("ROLE_USER");
        Optional<Country> country = countryService.getCountryById(request.getUserCountryId());

        var user = User.builder()
                .username(request.getUsername())
                .userEmail(request.getUserEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(role.get())
                .userCountry(country.get())
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse registerUser(SignUpRequest request) {
        Optional<Role> role = roleService.getRoleById(request.getUserRoleId());
        Optional<Country> country = countryService.getCountryById(request.getUserCountryId());

        var user = User.builder()
                .username(request.getUsername())
                .userEmail(request.getUserEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .userRole(role.get())
                .userCountry(country.get())
                .build();

        userService.create(user);

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }

    public JwtAuthenticationResponse signIn(SignInRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername(),
                request.getPassword()
        ));

        var user = userService
                .userDetailsService()
                .loadUserByUsername(request.getUsername());

        var jwt = jwtService.generateToken(user);
        return new JwtAuthenticationResponse(jwt);
    }
}
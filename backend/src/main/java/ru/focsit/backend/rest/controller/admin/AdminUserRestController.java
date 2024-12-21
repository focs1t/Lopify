package ru.focsit.backend.rest.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.dto.JwtAuthenticationResponse;
import ru.focsit.backend.dto.SignUpRequest;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.dto.UserDto;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.AuthenticationService;
import ru.focsit.backend.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminUserRestController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping
    public JwtAuthenticationResponse registerUser(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.registerUser(request);
    }
}

package ru.focsit.backend.rest.controller.admin;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import ru.focsit.backend.dto.JwtAuthenticationResponse;
import ru.focsit.backend.dto.SignUpRequest;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.AuthenticationService;
import ru.focsit.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class UserRestController {

    @Autowired
    private UserService userService;

    private final AuthenticationService authenticationService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public JwtAuthenticationResponse registerUser(@RequestBody @Valid SignUpRequest request) {
        return authenticationService.registerUser(request);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = userService.updateUser(id, userDetails);
        return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}
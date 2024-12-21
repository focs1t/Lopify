package ru.focsit.backend.service;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.focsit.backend.dto.SongDto;
import ru.focsit.backend.dto.UserDto;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongService songService;

    public UserDto toDto(User user) {
        return new UserDto(
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole().toString()
        );
    }

    public User fromDto(UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .role(Role.valueOf(userDto.getRole()))
                .build();
    }

    public User createUser(UserDto userDto) {
        User user = fromDto(userDto);
        return userRepository.save(user);
    }

    public List<UserDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public User updateUser(Long id, User updatedUser) {
        User user = getUserById(id);
        user.setUsername(updatedUser.getUsername());
        user.setEmail(updatedUser.getEmail());
        user.setRole(updatedUser.getRole());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User create(@Valid User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        User createdUser = userRepository.save(user);

        return createdUser;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public UserDto getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = getByUsername(username);
        return toDto(user);
    }
}
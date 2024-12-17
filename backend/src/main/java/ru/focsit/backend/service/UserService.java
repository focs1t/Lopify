package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Playlist;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.CountryRepository;
import ru.focsit.backend.repository.PlaylistRepository;
import ru.focsit.backend.repository.RoleRepository;
import ru.focsit.backend.repository.UserRepository;

import jakarta.validation.Valid;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private CountryRepository countryRepository;

    public User createUser(@Valid User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateUser(Long userId, @Valid User userDetails) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User curUser = user.get();
            curUser.setUsername(userDetails.getUsername());
            //if (userDetails.getPassword() != null && !userDetails.getPassword().isEmpty()) {
            //    curUser.setPassword(passwordEncoder.encode(userDetails.getPassword()));
            //}
            curUser.setPassword(userDetails.getPassword());
            curUser.setUserEmail(userDetails.getUserEmail());
            curUser.setUserRole(userDetails.getUserRole());
            curUser.setUserCountry(userDetails.getUserCountry());
            return userRepository.save(curUser);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> searchUsers(String query) {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().contains(query))
                .collect(Collectors.toList());
    }

    public User getLopifyUser() {
        return getAllUsers().stream()
                .filter(user -> user.getUsername().equals("Lopify"))
                .findFirst()
                .orElseThrow();
    }

    public User findByUserLogin(String curUserName) {
        return searchUsers(curUserName).stream().findFirst().orElseThrow();
    }

    public Playlist createFavoritePlaylist(User user) {
        Playlist favoritePlaylist = new Playlist();
        favoritePlaylist.setPlaylistName("Favorite");
        favoritePlaylist.setPlaylistDescription("This is the user's favorite playlist.");
        favoritePlaylist.setPlaylistImageUrl("static/uploads/heart.png");
        favoritePlaylist.setPlaylistUser(user);

        return playlistRepository.save(favoritePlaylist);
    }

    public User create(@Valid User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        User createdUser = userRepository.save(user);

        createFavoritePlaylist(createdUser);

        return createdUser;
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));
    }

    public UserDetailsService userDetailsService() {
        return this::getByUsername;
    }

    public User getCurrentUser() {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(username);
    }
}
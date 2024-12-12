package ru.focsit.backend.service;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.CountryRepository;
import ru.focsit.backend.repository.RoleRepository;
import ru.focsit.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    private CountryRepository countryRepository;

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId) {
        return userRepository.findById(userId);
    }

    public User updateUser(Long userId, User userDetails) {
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

    public User create(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Пользователь с таким именем уже существует");
        }

        if (userRepository.existsByUserEmail(user.getUserEmail())) {
            throw new RuntimeException("Пользователь с таким email уже существует");
        }

        return createUser(user);
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


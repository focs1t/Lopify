package ru.focsit.backend.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.pojo.UserRegistrationDto;
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

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void createUser(UserRegistrationDto userRegistrationDto) {
        Country country = countryRepository.findById(userRegistrationDto.getUserCountryId())
                .orElseThrow(() -> new IllegalArgumentException("Country not found"));

        User user = new User();
        Role role = roleRepository.findById(userRegistrationDto.getUserRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setUserLogin(userRegistrationDto.getUserLogin());
        user.setUserPassword(passwordEncoder.encode(userRegistrationDto.getUserPassword()));
        user.setUserEmail(userRegistrationDto.getUserEmail());
        user.setUserRole(role);
        user.setUserCountry(country);

        userRepository.save(user);
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
            curUser.setUserLogin(userDetails.getUserLogin());
            if (userDetails.getUserPassword() != null && !userDetails.getUserPassword().isEmpty()) {
                curUser.setUserPassword(passwordEncoder.encode(userDetails.getUserPassword()));
            }
            curUser.setUserEmail(userDetails.getUserEmail());
            curUser.setUserRole(userDetails.getUserRole());
            curUser.setUserCountry(userDetails.getUserCountry());
            return userRepository.save(curUser);
        }
        return null;
    }

    public User updateUserself(Long userId, User userDetails) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User curUser = user.get();
            curUser.setUserLogin(userDetails.getUserLogin());
            curUser.setUserEmail(userDetails.getUserEmail());
            return userRepository.save(curUser);
        }
        return null;
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    public List<User> searchUsers(String query) {
        return getAllUsers().stream()
                .filter(user -> user.getUserLogin().contains(query))
                .collect(Collectors.toList());
    }

    public User getLopifyUser() {
        return getAllUsers().stream()
                .filter(user -> user.getUserLogin().equals("Lopify"))
                .findFirst()
                .orElseThrow();
    }

    public User findByUserLogin(String curUserName) {
        return searchUsers(curUserName).stream().findFirst().orElseThrow();
    }
}


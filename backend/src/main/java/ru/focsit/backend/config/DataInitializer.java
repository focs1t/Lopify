package ru.focsit.backend.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.UserService;

@Component
public class DataInitializer {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    public void init() {
        initAdminUser();
    }

    private void initAdminUser() {
        if (userService.getAllUsers().stream().noneMatch(u -> u.getUsername().equals("admin"))) {
            User adminUser = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .email("admin@music.com")
                    .role(Role.ROLE_ADMIN)
                    .build();

            userService.create(adminUser);
        }
    }
}
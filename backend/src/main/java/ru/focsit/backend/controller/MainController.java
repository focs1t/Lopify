package ru.focsit.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.pojo.UserRegistrationDto;
import ru.focsit.backend.repository.CountryRepository;
import ru.focsit.backend.repository.RoleRepository;
import ru.focsit.backend.service.UserService;

import java.util.List;
import java.util.Optional;

@Controller
public class MainController {

    @GetMapping("/")
    public String redirectWithUsingRedirectPrefix() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_ADMIN"))) {
            return "redirect:/admin";
        } else if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ROLE_MODERATOR"))) {
            return "redirect:/moderator";
        } else {
            return "index";
        }
    }

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CountryRepository countryRepository;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userRegistrationDto", new UserRegistrationDto());
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute UserRegistrationDto userRegistrationDto, Model model) {
        try {
            // Устанавливаем роль ROLE_USER автоматически
            Optional<Role> userRole = roleRepository.findByRoleName("ROLE_USER");
            userRegistrationDto.setUserRoleId(userRole.get().getRoleId());
            userService.registerUser(userRegistrationDto);
            model.addAttribute("successMessage", "User registered successfully");
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Error registering user: " + e.getMessage());
        }
        return "index";
    }
}
package ru.focsit.backend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import ru.focsit.backend.pojo.Country;
import ru.focsit.backend.pojo.Role;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.CountryRepository;
import ru.focsit.backend.repository.RoleRepository;
import ru.focsit.backend.repository.UserRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Получаем список пользователей
    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "admin/users/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new User());
        List<Country> countries = countryRepository.findAll();
        List<Role> roles = roleRepository.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("roles", roles);
        return "admin/users/new";
    }

    @PostMapping
    public String createUser(@ModelAttribute User user) {
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        userRepository.save(user);
        return "redirect:/admin/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        List<Role> roles = roleRepository.findAll();
        List<Country> countries = countryRepository.findAll();

        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        model.addAttribute("countries", countries);

        return "admin/users/edit";
    }

    // Обрабатываем изменения пользователя
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable Long id, @ModelAttribute User user) {
        User existingUser = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        existingUser.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        existingUser.setUserRole(user.getUserRole());
        existingUser.setUserCountry(user.getUserCountry());
        userRepository.save(existingUser);
        return "redirect:/admin/users";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(user);
        return "redirect:/admin/users";
    }
}
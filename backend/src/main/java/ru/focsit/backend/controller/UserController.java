package ru.focsit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // TODO получить всех пользователей

    // TODO все роли
    // Получить данные пользователя
    @GetMapping("/{id}")
    public User getUserDetails(@PathVariable Long id) {
        // TODO отображение списка любимого все роли
        return userService.getUserById(id);
    }

    // TODO без авторизации
    // Регистрация пользователя
    @PostMapping("/register")
    public User registerUser(@RequestBody @Valid User user) {
        return userService.registerUser(user);
    }

    // TODO без авторизации
    // Авторизация пользователя
    @PostMapping("/login")
    public String loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

    // TODO удаление пользователя админом

    // TODO выдача роли админом
}
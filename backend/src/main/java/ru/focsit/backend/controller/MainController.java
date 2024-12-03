package ru.focsit.backend.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.service.UserService;

@Controller
public class MainController {
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("message", "Welcome to the start page!");
        return "index";
    }

    @Autowired
    private UserService userService;

    @GetMapping("/sign-up")
    public String registration(Model model) {
        model.addAttribute("userForm", new User());

        return "sign-up";
    }

    @PostMapping("/sign-up")
    public String addUser(@ModelAttribute("userForm") @Valid User userForm, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "sign-up";
        }
        if (!userForm.getUserPassword().equals(userForm.getUserPasswordConfirm())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "sign-up";
        }
        /*if (!userService.saveUser(userForm)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
            return "sign-up";
        }*/

        return "redirect:/";
    }
}

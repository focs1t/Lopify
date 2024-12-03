package ru.focsit.backend.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.focsit.backend.pojo.User;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String adminPage(Model model) {
        model.addAttribute("message", "Welcome to the admin page!");
        return "admin";
    }

    @GetMapping("/admin/countries")
    public String countryPage(Model model) {
        model.addAttribute("countryPage", new User());

        return "admin/countries/list";
    }

    @GetMapping("/admin/genres")
    public String genrePage(Model model) {
        model.addAttribute("genrePage", new User());

        return "admin/genres/list";
    }

    @GetMapping("/admin/roles")
    public String rolePage(Model model) {
        model.addAttribute("rolePage", new User());

        return "admin/roles/list";
    }

    @GetMapping("/admin/users")
    public String userPage(Model model) {
        model.addAttribute("userPage", new User());

        return "admin/users/list";
    }
}

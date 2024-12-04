package ru.focsit.backend.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.focsit.backend.pojo.User;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String index() {
        return "admin/admin-page";
    }
}

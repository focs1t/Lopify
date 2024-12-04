package ru.focsit.backend.controller.moderator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.focsit.backend.pojo.User;

@Controller
@RequestMapping("/moderator")
public class ModeratorController {
    @GetMapping
    public String index() {
        return "moderator/moderator-page";
    }
}

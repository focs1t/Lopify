package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Comment;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.CommentRepository;
import ru.focsit.backend.repository.UserRepository;

import jakarta.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/moderator/users")
public class UserProfileController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @GetMapping
    public String listUserProfiles(Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "moderator/users/list";
    }

    @GetMapping("/{id}")
    public String viewUserDetails(@PathVariable Long id, Model model) {
        User user = userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Comment> comments = commentRepository.findByCommentUser(user);
        model.addAttribute("user", user);
        model.addAttribute("comments", comments);
        return "moderator/users/details";
    }
}
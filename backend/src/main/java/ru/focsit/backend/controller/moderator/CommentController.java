package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.util.List;

@Controller
@RequestMapping("/moderator/comments")
public class CommentController {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping
    public String listComments(Model model) {
        List<Comment> comments = commentRepository.findAll();
        model.addAttribute("comments", comments);
        return "moderator/comments/list";
    }

    @GetMapping("/edit/{id}")
    public String editComment(@PathVariable Long id, Model model) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));

        List<User> users = userRepository.findAll();
        List<Album> albums = albumRepository.findAll();

        model.addAttribute("comment", comment);
        model.addAttribute("users", users);
        model.addAttribute("albums", albums);

        return "moderator/comments/edit";
    }

    @PostMapping("/update/{id}")
    public String updateComment(@PathVariable Long id, @ModelAttribute Comment comment) {
        Comment existingComment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));
        existingComment.setCommentText(comment.getCommentText());
        existingComment.setCommentAlbum(comment.getCommentAlbum());
        existingComment.setCommentUser(comment.getCommentUser());
        commentRepository.save(existingComment);
        return "redirect:/moderator/comments";
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid comment Id:" + id));
        commentRepository.delete(comment);
        return "redirect:/moderator/comments";
    }
}
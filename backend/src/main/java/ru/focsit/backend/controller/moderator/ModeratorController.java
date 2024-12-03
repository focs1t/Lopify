package ru.focsit.backend.controller.moderator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.focsit.backend.pojo.User;

@Controller
public class ModeratorController {
    @GetMapping("/moderator")
    public String moderatorPage(Model model) {
        model.addAttribute("message", "Welcome to the moderator page!");
        return "moderator";
    }

    @GetMapping("/moderator/albums")
    public String albumPage(Model model) {
        model.addAttribute("albumPage", new User());

        return "moderator/albums/list";
    }

    @GetMapping("/moderator/artists")
    public String artistPage(Model model) {
        model.addAttribute("artistPage", new User());

        return "moderator/artists/list";
    }

    @GetMapping("/moderator/comments")
    public String commentPage(Model model) {
        model.addAttribute("commentPage", new User());

        return "moderator/comments/list";
    }

    @GetMapping("/moderator/concerts")
    public String concertPage(Model model) {
        model.addAttribute("concertPage", new User());

        return "moderator/concerts/list";
    }

    @GetMapping("/moderator/playlists")
    public String playlistPage(Model model) {
        model.addAttribute("playlistPage", new User());

        return "moderator/playlists/list";
    }

    @GetMapping("/moderator/tours")
    public String tourPage(Model model) {
        model.addAttribute("tourPage", new User());

        return "moderator/tours/list";
    }

    @GetMapping("/moderator/tracks")
    public String trackPage(Model model) {
        model.addAttribute("trackPage", new User());

        return "moderator/tracks/list";
    }

    @GetMapping("/moderator/userProfiles")
    public String userProfilePage(Model model) {
        model.addAttribute("userProfilePage", new User());

        return "moderator/userProfiles/list";
    }
}

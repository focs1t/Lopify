package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@RequestMapping("/moderator/albums")
public class AlbumController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private TrackRepository trackRepository;

    @GetMapping
    public String listAlbums(Model model) {
        List<Album> albums = albumRepository.findAll();
        model.addAttribute("albums", albums);
        return "moderator/albums/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("album", new Album());
        List<Genre> genres = genreRepository.findAll();
        List<Artist> artists = artistRepository.findAll();
        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);
        return "moderator/albums/new";
    }

    @GetMapping("/{id}")
    public String viewAlbumDetails(@PathVariable Long id, Model model) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        List<Track> tracks = trackRepository.findByTrackAlbum(album);
        List<Comment> comments = commentRepository.findByCommentAlbum(album);
        model.addAttribute("album", album);
        model.addAttribute("tracks", tracks);
        model.addAttribute("comments", comments);
        return "moderator/albums/details";
    }

    @PostMapping
    public String createAlbum(@ModelAttribute Album album, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("uploads/" + file.getOriginalFilename());
                Files.write(path, bytes);
                album.setAlbumImageUrl("/uploads/" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        albumRepository.save(album);
        return "redirect:/moderator/albums";
    }

    @GetMapping("/edit/{id}")
    public String editAlbum(@PathVariable Long id, Model model) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));

        List<Genre> genres = genreRepository.findAll();
        List<Artist> artists = artistRepository.findAll();

        model.addAttribute("album", album);
        model.addAttribute("genres", genres);
        model.addAttribute("artists", artists);

        return "moderator/albums/edit";
    }

    @PostMapping("/update/{id}")
    public String updateAlbum(@PathVariable Long id, @ModelAttribute Album album, @RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        Album existingAlbum = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        existingAlbum.setAlbumName(album.getAlbumName());
        existingAlbum.setAlbumReleaseDate(album.getAlbumReleaseDate());
        existingAlbum.setAlbumArtist(album.getAlbumArtist());
        existingAlbum.setAlbumGenre(album.getAlbumGenre());
        existingAlbum.setAlbumDescription(album.getAlbumDescription());

        if (!file.isEmpty()) {
            try {
                byte[] bytes = file.getBytes();
                Path path = Paths.get("uploads/" + file.getOriginalFilename());
                Files.write(path, bytes);
                existingAlbum.setAlbumImageUrl("/uploads/" + file.getOriginalFilename());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        albumRepository.save(existingAlbum);
        return "redirect:/moderator/albums";
    }

    @GetMapping("/delete/{id}")
    public String deleteAlbum(@PathVariable Long id) {
        Album album = albumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid album Id:" + id));
        albumRepository.delete(album);
        return "redirect:/moderator/albums";
    }
}
package ru.focsit.backend.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Genre;
import ru.focsit.backend.repository.GenreRepository;

import java.util.List;

@Controller
@RequestMapping("/admin/genres")
public class GenreController {

    @Autowired
    private GenreRepository genreRepository;

    // Отображение списка жанров
    @GetMapping
    public String getAllGenres(Model model) {
        List<Genre> genres = genreRepository.findAll();
        model.addAttribute("genres", genres);
        return "admin/genres/list";
    }

    // Формуляр для создания нового жанра
    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("genre", new Genre());
        return "admin/genres/new";
    }

    // Создание нового жанра
    @PostMapping
    public String createGenre(@ModelAttribute Genre genre) {
        genreRepository.save(genre);
        return "redirect:/admin/genres";
    }

    // Формуляр для редактирования жанра
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        model.addAttribute("genre", genre);
        return "admin/genres/edit";
    }

    // Обновление жанра
    @PostMapping("/update/{id}")
    public String updateGenre(@PathVariable Long id, @ModelAttribute Genre genreDetails) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        genre.setGenreName(genreDetails.getGenreName());
        genreRepository.save(genre);
        return "redirect:/admin/genres";
    }

    // Удаление жанра
    @GetMapping("/delete/{id}")
    public String deleteGenre(@PathVariable Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        genreRepository.delete(genre);
        return "redirect:/admin/genres";
    }
}


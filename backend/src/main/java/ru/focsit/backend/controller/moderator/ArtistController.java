package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.util.List;

@Controller
@RequestMapping("/moderator/artists")
public class ArtistController {

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private TourRepository tourRepository;

    @GetMapping
    public String listArtists(Model model) {
        List<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "moderator/artists/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("artist", new Artist());
        return "moderator/artists/new";
    }

    @GetMapping("/{id}")
    public String viewArtistDetails(@PathVariable Long id, Model model) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));
        List<Tour> tours = tourRepository.findByTourArtist(artist);
        List<Concert> concerts = tours.stream()
                .flatMap(tour -> concertRepository.findByConcertTour(tour).stream())
                .toList();
        model.addAttribute("artist", artist);
        model.addAttribute("concerts", concerts);
        return "moderator/artists/details";
    }

    @PostMapping
    public String createArtist(@ModelAttribute Artist artist) {
        artistRepository.save(artist);
        return "redirect:/moderator/artists";
    }

    @GetMapping("/edit/{id}")
    public String editArtist(@PathVariable Long id, Model model) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));
        model.addAttribute("artist", artist);
        return "moderator/artists/edit";
    }

    @PostMapping("/update/{id}")
    public String updateArtist(@PathVariable Long id, @ModelAttribute Artist artist) {
        Artist existingArtist = artistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));
        existingArtist.setArtistName(artist.getArtistName());
        existingArtist.setArtistBio(artist.getArtistBio());
        existingArtist.setArtistCountry(artist.getArtistCountry());
        artistRepository.save(existingArtist);
        return "redirect:/moderator/artists";
    }

    @GetMapping("/delete/{id}")
    public String deleteArtist(@PathVariable Long id) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));
        artistRepository.delete(artist);
        return "redirect:/moderator/artists";
    }
}
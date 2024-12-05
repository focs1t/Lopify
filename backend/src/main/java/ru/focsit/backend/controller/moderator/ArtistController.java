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
    private CountryRepository countryRepository;

    @Autowired
    private AlbumRepository albumRepository;

    @GetMapping
    public String listArtists(Model model) {
        List<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "moderator/artists/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("artist", new Artist());
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
        return "moderator/artists/new";
    }

    @GetMapping("/{id}")
    public String viewArtistDetails(@PathVariable Long id, Model model) {
        Artist artist = artistRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid artist Id:" + id));
        List<Album> albums = albumRepository.findByAlbumArtist(artist);
        model.addAttribute("artist", artist);
        model.addAttribute("albums", albums);
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
        List<Country> countries = countryRepository.findAll();
        model.addAttribute("countries", countries);
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
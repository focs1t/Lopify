package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.util.List;

@Controller
@RequestMapping("/moderator/concerts")
public class ConcertController {

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private TourRepository tourRepository;

    @GetMapping
    public String listConcerts(Model model) {
        List<Concert> concerts = concertRepository.findAll();
        model.addAttribute("concerts", concerts);
        return "moderator/concerts/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("concert", new Concert());
        List<Country> countries = countryRepository.findAll();
        List<Tour> tours = tourRepository.findAll();
        model.addAttribute("countries", countries);
        model.addAttribute("tours", tours);
        return "moderator/concerts/new";
    }

    @PostMapping
    public String createConcert(@ModelAttribute Concert concert) {
        concertRepository.save(concert);
        return "redirect:/moderator/concerts";
    }

    @GetMapping("/edit/{id}")
    public String editConcert(@PathVariable Long id, Model model) {
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid concert Id:" + id));

        List<Country> countries = countryRepository.findAll();
        List<Tour> tours = tourRepository.findAll();

        model.addAttribute("concert", concert);
        model.addAttribute("countries", countries);
        model.addAttribute("tours", tours);

        return "moderator/concerts/edit";
    }

    @PostMapping("/update/{id}")
    public String updateConcert(@PathVariable Long id, @ModelAttribute Concert concert) {
        Concert existingConcert = concertRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid concert Id:" + id));
        existingConcert.setConcertPlace(concert.getConcertPlace());
        existingConcert.setConcertCity(concert.getConcertCity());
        existingConcert.setConcertTicketUrl(concert.getConcertCity());
        existingConcert.setConcertDate(concert.getConcertDate());
        existingConcert.setConcertCountry(concert.getConcertCountry());
        existingConcert.setConcertTour(concert.getConcertTour());
        concertRepository.save(existingConcert);
        return "redirect:/moderator/concerts";
    }

    @GetMapping("/delete/{id}")
    public String deleteConcert(@PathVariable Long id) {
        Concert concert = concertRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid concert Id:" + id));
        concertRepository.delete(concert);
        return "redirect:/moderator/concerts";
    }
}

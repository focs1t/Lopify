package ru.focsit.backend.controller.moderator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.*;
import ru.focsit.backend.repository.*;

import java.util.List;

@Controller
@RequestMapping("/moderator/tours")
public class TourController {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @GetMapping
    public String listTours(Model model) {
        List<Tour> tours = tourRepository.findAll();
        model.addAttribute("tours", tours);
        return "moderator/tours/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("tour", new Tour());
        List<Artist> artists = artistRepository.findAll();
        model.addAttribute("artists", artists);
        return "moderator/tours/new";
    }

    @PostMapping
    public String createTour(@ModelAttribute Tour tour) {
        tourRepository.save(tour);
        return "redirect:/moderator/tours";
    }

    @GetMapping("/edit/{id}")
    public String editTour(@PathVariable Long id, Model model) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid tour Id:" + id));

        List<Artist> artists = artistRepository.findAll();

        model.addAttribute("tour", tour);
        model.addAttribute("artists", artists);

        return "moderator/tours/edit";
    }

    @PostMapping("/update/{id}")
    public String updateTour(@PathVariable Long id, @ModelAttribute Tour tour) {
        Tour existingTour = tourRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid tour Id:" + id));
        existingTour.setTourName(tour.getTourName());
        existingTour.setTourArtist(tour.getTourArtist());
        tourRepository.save(existingTour);
        return "redirect:/moderator/tours";
    }

    @GetMapping("/delete/{id}")
    public String deleteTour(@PathVariable Long id) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid tour Id:" + id));
        tourRepository.delete(tour);
        return "redirect:/moderator/tours";
    }
}
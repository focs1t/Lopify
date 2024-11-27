package ru.focsit.backend.services;

import ru.focsit.backend.pojo.Tour;
import ru.focsit.backend.repositories.TourRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TourService {

    @Autowired
    private TourRepository tourRepository;

    public List<Tour> getAllTours() {
        return tourRepository.findAll();
    }

    public Optional<Tour> getTourById(Long tourId) {
        return tourRepository.findById(tourId);
    }

    public Tour createTour(Tour tour) {
        return tourRepository.save(tour);
    }

    public Tour updateTour(Long tourId, Tour tourDetails) {
        Optional<Tour> tour = tourRepository.findById(tourId);
        if (tour.isPresent()) {
            Tour curTour = tour.get();
            curTour.setTourName(tourDetails.getTourName());
            curTour.setTourArtist(tourDetails.getTourArtist());
            return tourRepository.save(curTour);
        }
        return null;
    }

    public void deleteTour(Long tourId) {
        tourRepository.deleteById(tourId);
    }
}

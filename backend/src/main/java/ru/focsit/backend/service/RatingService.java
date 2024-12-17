package ru.focsit.backend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.focsit.backend.pojo.Rating;
import ru.focsit.backend.pojo.Song;
import ru.focsit.backend.pojo.User;
import ru.focsit.backend.repository.RatingRepository;
import ru.focsit.backend.repository.SongRepository;
import ru.focsit.backend.repository.UserRepository;

import java.util.List;

@Service
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    public Rating rateSong(Long userId, Long songId, int ratingValue) {
        Song song = songRepository.findById(songId).orElseThrow(() -> new IllegalArgumentException("Song not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("User not found"));

        Rating rating = new Rating();
        rating.setUser(user);
        rating.setSong(song);
        rating.setRating(ratingValue);

        return ratingRepository.save(rating);
    }

    public double getSongAverageRating(Long songId) {
        List<Rating> ratings = ratingRepository.findAll();
        return ratings.stream()
                .filter(rating -> rating.getSong().getId().equals(songId))
                .mapToInt(Rating::getRating)
                .average()
                .orElse(0);
    }
}
package ru.focsit.backend.service;

import ru.focsit.backend.pojo.Concert;
import ru.focsit.backend.repository.ConcertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConcertService {

    @Autowired
    private ConcertRepository concertRepository;

    public List<Concert> getAllConcerts() {
        return concertRepository.findAll();
    }

    public Optional<Concert> getConcertById(Long concertId) {
        return concertRepository.findById(concertId);
    }

    public Concert createConcert(Concert concert) {
        return concertRepository.save(concert);
    }

    public Concert updateConcert(Long concertId, Concert concertDetails) {
        Optional<Concert> concert = concertRepository.findById(concertId);
        if (concert.isPresent()) {
            Concert curConcert = concert.get();
            curConcert.setConcertPlace(concertDetails.getConcertPlace());
            curConcert.setConcertCity(concertDetails.getConcertCity());
            curConcert.setConcertTicketUrl(concertDetails.getConcertTicketUrl());
            curConcert.setConcertDate(concertDetails.getConcertDate());
            curConcert.setConcertCountry(concertDetails.getConcertCountry());
            curConcert.setConcertTour(concertDetails.getConcertTour());
            return concertRepository.save(curConcert);
        }
        return null;
    }

    public void deleteConcert(Long concertId) {
        concertRepository.deleteById(concertId);
    }
}

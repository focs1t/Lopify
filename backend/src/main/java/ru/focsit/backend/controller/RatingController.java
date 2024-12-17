package ru.focsit.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.focsit.backend.pojo.Rating;
import ru.focsit.backend.service.RatingService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

    @Autowired
    private RatingService ratingService;

    // TODO топ 10 треков по популярности все роли
}
package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.CreateRatingRequest;
import com.apps.bookclub.dtos.RatingResponse;
import com.apps.bookclub.entities.Rating;
import com.apps.bookclub.services.RatingService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ratings")
public class RatingController {

    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping
    public RatingResponse createRating(
            @RequestBody CreateRatingRequest request) {

        Rating rating = ratingService.createRating(
                request.memberId(),
                request.bookId(),
                request.score()
        );
        return new RatingResponse(
                rating.getId(),
                rating.getMember().getId(),
                rating.getBook().getId(),
                rating.getScore()
        );
    }

    @PutMapping
    public Rating updateRating(
            @RequestBody CreateRatingRequest request) {

        return ratingService.updateRating(
                request.memberId(),
                request.bookId(),
                request.score()
        );
    }
}
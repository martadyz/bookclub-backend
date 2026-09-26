package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.AdminCreateRatingRequest;
import com.apps.bookclub.dtos.CreateRatingRequest;
import com.apps.bookclub.dtos.RatingResponse;
import com.apps.bookclub.entities.Rating;
import com.apps.bookclub.services.RatingService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
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
            @RequestBody CreateRatingRequest request,
            Authentication authentication) {

        Rating rating = ratingService.createRating(
                authentication.getName(),
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
    public RatingResponse updateRating(
            @RequestBody CreateRatingRequest request,
            Authentication authentication) {

        Rating rating =  ratingService.updateRating(
                authentication.getName(),
                request.bookId(),
                request.score()
        );

        return new RatingResponse(
                rating.getId(),
                rating.getMember().getId(),
                rating.getBook().getId(),
                rating.getScore());
    }

    @PostMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public RatingResponse createRatingForMember(
            @RequestBody AdminCreateRatingRequest request) {

        Rating rating = ratingService.createRatingForMember(
                request.memberId(),
                request.bookId(),
                request.score()
        );
        return new RatingResponse(
                rating.getId(),
                rating.getMember().getId(),
                rating.getBook().getId(),
                rating.getScore());
    }

    @PutMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public RatingResponse updateRatingForMember(
            @RequestBody AdminCreateRatingRequest request) {

        Rating rating =  ratingService.updateRatingForMember(
                request.memberId(),
                request.bookId(),
                request.score()
        );
        return new RatingResponse(
                rating.getId(),
                rating.getMember().getId(),
                rating.getBook().getId(),
                rating.getScore());
    }
}
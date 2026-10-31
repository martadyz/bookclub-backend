package com.apps.bookclub.dtos;

import java.time.LocalDate;
import java.util.List;

public record BookResponse(
        Long id,
        String title,
        String author,
        LocalDate meetingDate,
        double averageRating,
        List<BookRatingResponse> ratings
) {
}

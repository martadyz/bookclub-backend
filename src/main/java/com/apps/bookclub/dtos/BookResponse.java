package com.apps.bookclub.dtos;

import java.time.LocalDate;
import java.util.List;

public record BookResponse(
        Long id,
        String title,
        LocalDate meetingDate,
        double averageRating,
        List<BookRatingResponse> ratings
) {
}

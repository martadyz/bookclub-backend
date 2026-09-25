package com.apps.bookclub.dtos;

import java.time.LocalDate;

public record BookResponse(
        Long id,
        String title,
        LocalDate meetingDate,
        double averageRating
) {
}

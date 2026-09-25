package com.apps.bookclub.dtos;

import java.time.LocalDate;

public record CreateBookRequest(
        String title,
        LocalDate meetingDate
) {}

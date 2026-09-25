package com.apps.bookclub.dtos;

public record RatingResponse(
        Long id,
        Long memberId,
        Long bookId,
        Double score
) {}

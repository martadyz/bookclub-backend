package com.apps.bookclub.dtos;

public record AdminCreateRatingRequest(
        Long memberId,
        Long bookId,
        Double score
) {}
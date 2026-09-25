package com.apps.bookclub.dtos;

public record CreateRatingRequest(
        Long memberId,
        Long bookId,
        Double score
) {
}
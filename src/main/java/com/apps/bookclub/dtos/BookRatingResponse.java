package com.apps.bookclub.dtos;

public record BookRatingResponse(String memberName,
                                 Long memberId,
                                 Double memberRating) {
}

package com.apps.bookclub.dtos;

public record MemberRatingsResponse(Long id,
                                    Long bookId,
                                    String bookTitle,
                                    Double score) {
}

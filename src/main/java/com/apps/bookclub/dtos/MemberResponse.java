package com.apps.bookclub.dtos;

import com.apps.bookclub.enums.Role;

import java.util.List;

public record MemberResponse(Long id,
                             String name,
                             double averageRating,
                             List<MemberRatingsResponse> ratings,
                             Role role
) {
}

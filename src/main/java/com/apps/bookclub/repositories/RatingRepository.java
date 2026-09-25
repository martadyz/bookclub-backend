package com.apps.bookclub.repositories;

import com.apps.bookclub.entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    boolean existsByMemberIdAndBookId(
            Long memberId,
            Long bookId
    );

    Optional<Rating> findByMemberIdAndBookId(Long memberId, Long bookId);

    List<Rating> findByBookId(Long bookId);
}

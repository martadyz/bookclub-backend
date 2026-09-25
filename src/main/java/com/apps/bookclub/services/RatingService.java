package com.apps.bookclub.services;

import com.apps.bookclub.entities.Book;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.entities.Rating;
import com.apps.bookclub.repositories.BookRepository;
import com.apps.bookclub.repositories.MemberRepository;
import com.apps.bookclub.repositories.RatingRepository;
import org.springframework.stereotype.Service;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public RatingService(
            RatingRepository ratingRepository,
            MemberRepository memberRepository,
            BookRepository bookRepository) {

        this.ratingRepository = ratingRepository;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }

    public Rating createRating(
            Long memberId,
            Long bookId,
            Double score) {

        if (score < 1 || score > 10) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 10");
        }

        if (ratingRepository.existsByMemberIdAndBookId(
                memberId, bookId)) {

            throw new IllegalStateException(
                    "Member has already rated this book");
        }

        Member member = memberRepository.findById(memberId)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));

        Rating rating = new Rating(member, book, score);

        return ratingRepository.save(rating);
    }

    public Rating updateRating(
            Long memberId,
            Long bookId,
            Double score) {

        if (score < 1 || score > 10) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 10"
            );
        }

        Rating rating = ratingRepository
                .findByMemberIdAndBookId(memberId, bookId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rating not found"));

        rating.setScore(score);

        return ratingRepository.save(rating);
    }
}

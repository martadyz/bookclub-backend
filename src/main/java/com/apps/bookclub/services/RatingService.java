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

    private void validateScore(Double score) {
        if (score < 1 || score > 10) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 10");
        }
    }

    private Member findMemberByUsername(String loggedInUsername) {
       return memberRepository
                .findByName(loggedInUsername)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));
    }

    private Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
                .orElseThrow(() ->
                        new RuntimeException("Book not found"));
    }

    private Member findMemberById(Long memberId) {
        return memberRepository
                .findById(memberId)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));
    }

    private Rating findRatingByMemberIdAndBookId(Long memberId, Long bookId) {
        return ratingRepository
                .findByMemberIdAndBookId(
                        memberId,
                        bookId
                )
                .orElseThrow(() ->
                        new RuntimeException(
                                "Rating not found"
                        ));
    }

    private void checkIfMemberAndBookExists(Member member, Long bookId) {
        if (ratingRepository
                .existsByMemberIdAndBookId(
                        member.getId(),
                        bookId)) {

            throw new IllegalStateException(
                    "Member has already rated this book"
            );
        }
    }


    public Rating createRating(
            String loggedInUsername,
            Long bookId,
            Double score) {

        Member member = findMemberByUsername(loggedInUsername);
        validateScore(score);

        checkIfMemberAndBookExists(member, bookId);

        Book book = findBookById(bookId);

        Rating rating = new Rating(member, book, score);

        return ratingRepository.save(rating);
    }

    public Rating updateRating(
            String loggedInUsername,
            Long bookId,
            Double score) {

        Member member = findMemberByUsername(loggedInUsername);
        validateScore(score);

        Rating rating = findRatingByMemberIdAndBookId(member.getId(), bookId);

        rating.setScore(score);

        return ratingRepository.save(rating);
    }

    public Rating createRatingForMember(
            Long memberId,
            Long bookId,
            Double score) {

        validateScore(score);
        Member member = findMemberById(memberId);
        Book book = findBookById(bookId);

        checkIfMemberAndBookExists(member, bookId);

        Rating rating = new Rating(
                member,
                book,
                score
        );

        return ratingRepository.save(rating);
    }

    public Rating updateRatingForMember(
            Long memberId,
            Long bookId,
            Double score) {

        validateScore(score);

        Rating rating = findRatingByMemberIdAndBookId(memberId, bookId);

        rating.setScore(score);

        return ratingRepository.save(rating);
    }
}

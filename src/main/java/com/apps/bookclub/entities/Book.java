package com.apps.bookclub.entities;

import com.apps.bookclub.dtos.BookRatingResponse;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @OneToMany(mappedBy = "book")
    private List<Rating> ratings = new ArrayList<>();

    protected Book() {}

    public Book(String title, LocalDate meetingDate) {
        this.title = title;
        this.meetingDate = meetingDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDate getMeetingDate() {
        return meetingDate;
    }

    public List<BookRatingResponse> getRatings() {
        return ratings.stream()
                .map(rating -> new BookRatingResponse(
                        rating.getMember().getName(),
                        rating.getMember().getId(),
                        rating.getScore()
                ))
                .toList();
    }

    public double getAverageRating() {
        return ratings.stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }


}

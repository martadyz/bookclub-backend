package com.apps.bookclub.entities;

import com.apps.bookclub.dtos.BookRatingResponse;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private String author;

    @Column(name = "meeting_date", nullable = false)
    private LocalDate meetingDate;

    @OneToMany(mappedBy = "book")
    private List<Rating> ratings = new ArrayList<>();

    protected Book() {}

    public Book(String title, String author, LocalDate meetingDate) {
        this.title = title;
        this.author = author;
        this.meetingDate = meetingDate;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
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
        double average = ratings.stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);
        return BigDecimal.valueOf(average)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setMeetingDate(LocalDate meetingDate) {
        this.meetingDate = meetingDate;
    }


}

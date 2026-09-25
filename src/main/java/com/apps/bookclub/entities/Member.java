package com.apps.bookclub.entities;

import com.apps.bookclub.dtos.RatingResponse;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "member")
    private List<Rating> ratings = new ArrayList<>();

    protected Member() {
    }

    public Member(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }

    public List<RatingResponse> getRatings() {
        return ratings.stream()
                .map(rating -> new RatingResponse(
                        rating.getId(),
                        rating.getMember().getId(),
                        rating.getBook().getId(),
                        rating.getScore()
                ))
                .toList();
    }
}

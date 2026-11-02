package com.apps.bookclub.entities;

import com.apps.bookclub.dtos.MemberRatingsResponse;
import com.apps.bookclub.dtos.RatingResponse;
import com.apps.bookclub.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "members")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;


    @OneToMany(mappedBy = "member")
    private List<Rating> ratings = new ArrayList<>();

    @Transient
    private double averageRating;

    protected Member() {
    }

    public Member(String name, String password, Role role) {
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return this.name;
    }

    public Long getId() {
        return this.id;
    }
    @JsonIgnore
    public String getPassword() {
        return this.password;
    }
    public Role getRole() {
        return this.role;
    }

    public double getAverageRating() {
        averageRating = ratings.stream()
                .mapToDouble(Rating::getScore)
                .average()
                .orElse(0.0);
        return BigDecimal.valueOf(averageRating)
                .setScale(2, RoundingMode.HALF_UP)
                .doubleValue();
    }

    public List<MemberRatingsResponse> getRatings() {
        return ratings.stream()
                .map(rating -> new MemberRatingsResponse(
                        rating.getId(),
                        rating.getBook().getId(),
                        rating.getBook().getTitle(),
                        rating.getScore()
                ))
                .toList();
    }
}

package com.apps.bookclub.entities;

import com.apps.bookclub.dtos.RatingResponse;
import com.apps.bookclub.enums.Role;
import jakarta.persistence.*;

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
        this.name = password;
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
    public String getPassword() {
        return this.password;
    }
    public Role getRole() {
        return this.role;
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

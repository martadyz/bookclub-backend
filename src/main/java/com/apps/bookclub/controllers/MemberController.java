package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.ChangePasswordRequest;
import com.apps.bookclub.dtos.LoginRequest;
import com.apps.bookclub.dtos.MemberResponse;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.services.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    // CREATE (POST)
    @PostMapping
    public Member addMember(@RequestBody LoginRequest member) {
        return memberService.createMember(member);
    }

    // READ (GET ALL)
    @GetMapping
    public List<MemberResponse> getAllMembers(@RequestParam(required = false) String sort) {
        List<MemberResponse> members = memberService.getAllMembers().stream()
                .map(member -> new MemberResponse(
                        member.getId(),
                        member.getName(),
                        member.getAverageRating(),
                        member.getRatings(),
                        member.getRole()
                ))
                .toList();

        Comparator<MemberResponse> comparator = sort == null
                ? memberNameComparator()
                : memberComparator(sort);
        return members.stream().sorted(comparator).toList();
    }

    private Comparator<MemberResponse> memberComparator(String sort) {
        String[] sortParts = sort.split(",", -1);
        if (sortParts.length > 2) {
            throw invalidSortParameter();
        }

        Comparator<MemberResponse> comparator = switch (sortParts[0].trim()) {
            case "name" -> memberNameComparator();
            case "averageRating" -> Comparator.comparingDouble(MemberResponse::averageRating);
            default -> throw invalidSortParameter();
        };

        String direction = sortParts.length == 1
                ? "asc"
                : sortParts[1].trim().toLowerCase(Locale.ROOT);
        return switch (direction) {
            case "asc" -> comparator;
            case "desc" -> comparator.reversed();
            default -> throw invalidSortParameter();
        };
    }

    private Comparator<MemberResponse> memberNameComparator() {
        return Comparator.comparing(MemberResponse::name, String.CASE_INSENSITIVE_ORDER)
                .thenComparing(MemberResponse::name);
    }

    private ResponseStatusException invalidSortParameter() {
        return new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "sort must be name or averageRating, optionally followed by asc or desc"
        );
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @PutMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN')")
    public void changePassword(
            @PathVariable Long id,
            @RequestBody ChangePasswordRequest request) {
        memberService.changePassword(id, request.password());
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}

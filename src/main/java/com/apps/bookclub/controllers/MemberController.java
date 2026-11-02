package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.BookResponse;
import com.apps.bookclub.dtos.ChangePasswordRequest;
import com.apps.bookclub.dtos.LoginRequest;
import com.apps.bookclub.dtos.MemberResponse;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.services.MemberService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public List<MemberResponse> getAllMembers() {
        return memberService.getAllMembers().stream()
                .map(book -> new MemberResponse(
                        book.getId(),
                        book.getName(),
                        book.getAverageRating(),
                        book.getRatings(),
                        book.getRole()
                ))
                .toList();
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

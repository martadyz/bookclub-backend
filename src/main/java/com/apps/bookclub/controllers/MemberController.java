package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.CreateMemberRequest;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.services.MemberService;
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
    public Member addMember(@RequestBody CreateMemberRequest member) {
        return memberService.createMember(member);
    }

    // READ (GET ALL)
    @GetMapping
    public List<Member> getAllMembers() {
        return memberService.getAllMembers();
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        return memberService.getMember(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
}

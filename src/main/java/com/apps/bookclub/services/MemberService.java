package com.apps.bookclub.services;

import com.apps.bookclub.dtos.LoginRequest;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.enums.Role;
import com.apps.bookclub.repositories.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));
    }

//    public Member createMember(CreateMemberRequest request) {
//
//
//        Member member = new Member(
//                request.name()
//        );
//        return memberRepository.save(member);
//    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }

    public Member createMember(LoginRequest request) {

        if (memberRepository.existsByName(request.name())) {
            throw new IllegalStateException(
                    "Username already exists"
            );
        }

        String passwordHash =
                passwordEncoder.encode(request.password());

        Member member = new Member(
                request.name(),
                passwordHash,
                Role.USER
        );

        return memberRepository.save(member);
    }
}

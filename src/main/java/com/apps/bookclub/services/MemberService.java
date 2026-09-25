package com.apps.bookclub.services;

import com.apps.bookclub.dtos.CreateMemberRequest;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.repositories.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    public List<Member> getAllMembers() {
        return memberRepository.findAll();
    }

    public Member getMember(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Member not found"));
    }

    public Member createMember(CreateMemberRequest request) {
        Member member = new Member(
                request.name()
        );
        return memberRepository.save(member);
    }

    public void deleteMember(Long id) {
        memberRepository.deleteById(id);
    }
}

package com.apps.bookclub.services;

import com.apps.bookclub.entities.Member;
import com.apps.bookclub.repositories.MemberRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService
        implements UserDetailsService {

    private final MemberRepository memberRepository;

    public CustomUserDetailsService(
            MemberRepository memberRepository) {

        this.memberRepository = memberRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String name)
            throws UsernameNotFoundException {

        Member member = memberRepository.findByName(name)
                .orElseThrow(() ->
                        new UsernameNotFoundException(
                                "User not found"
                        ));

        return User.builder()
                .username(member.getName())
                .password(member.getPassword())
                .roles(member.getRole().name())
                .build();
    }
}
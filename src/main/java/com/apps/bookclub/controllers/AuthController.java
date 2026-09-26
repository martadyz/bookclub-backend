package com.apps.bookclub.controllers;

import com.apps.bookclub.dtos.LoginRequest;
import com.apps.bookclub.dtos.LoginResponse;
import com.apps.bookclub.entities.Member;
import com.apps.bookclub.repositories.MemberRepository;
import com.apps.bookclub.services.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    public AuthController(
            AuthenticationManager authenticationManager,
            MemberRepository memberRepository,
            MemberService memberService) {

        this.authenticationManager = authenticationManager;
        this.memberRepository = memberRepository;
        this.memberService = memberService;
    }

    @PostMapping("/login")
    public LoginResponse login(
            @RequestBody LoginRequest request,
            HttpServletRequest httpRequest) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.name(),
                                request.password()
                        )
                );

        SecurityContext context =
                SecurityContextHolder.createEmptyContext();

        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);

        HttpSession session =
                httpRequest.getSession(true);

        session.setAttribute(
                "SPRING_SECURITY_CONTEXT",
                context
        );

        Member member = memberRepository
                .findByName(request.name())
                .orElseThrow();

        return new LoginResponse(
                member.getId(),
                member.getName(),
                member.getRole().name()
        );
    }

    @PostMapping("/logout")
    public void logout(
            HttpServletRequest request) {

        SecurityContextHolder.clearContext();

        HttpSession session =
                request.getSession(false);

        if (session != null) {
            session.invalidate();
        }
    }
}

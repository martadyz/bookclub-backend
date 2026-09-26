package com.apps.bookclub.config;

import com.apps.bookclub.entities.Member;
import com.apps.bookclub.enums.Role;
import com.apps.bookclub.repositories.MemberRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminInitializer {

    @Bean
    CommandLineRunner createAdmin(
            MemberRepository memberRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            if (!memberRepository.existsByName("admin")) {

                Member admin = new Member(
                        "admin",
                        passwordEncoder.encode("admin123"),
                        Role.ADMIN
                );

                memberRepository.save(admin);
            }
        };
    }
}
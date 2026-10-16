package com.apps.bookclub.repositories;

import com.apps.bookclub.entities.Member;
import com.apps.bookclub.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByName(String name);

    List<Member> findAllByRoleNot(Role role);

    boolean existsByName(String name);
}

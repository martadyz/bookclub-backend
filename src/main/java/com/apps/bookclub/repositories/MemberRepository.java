package com.apps.bookclub.repositories;

import com.apps.bookclub.entities.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

}

package com.society.repositories;

import com.society.entities.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by Dilwar on 04-05-2017.
 */
public interface MemberRepository extends JpaRepository<Member, Integer> {
    Member findByMemberId(Integer memberId);

    List<Member> findAllByOrderByMemberIdAsc();

    Member findByEmailId(String email);
}

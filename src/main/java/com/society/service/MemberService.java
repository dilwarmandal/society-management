package com.society.service;


import com.society.entities.member.Member;
import com.society.vo.MemberVO;

import java.util.List;

public interface MemberService {

    void createMember(MemberVO userVO);

    Member findMemberDetails(Integer memberId);

    List<Member> findAllMembers();

    void removeMember(Integer memberId);
}

package com.society.service;

import com.society.config.exception.ApplicationCode;
import com.society.config.exception.SystemException;
import com.society.entities.member.Member;
import com.society.entities.member.MemberAddress;
import com.society.entities.member.MemberAddressPK;
import com.society.repositories.MemberRepository;
import com.society.util.HibernateUtil;
import com.society.vo.MemberVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("memberService")
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    HibernateUtil hibernateUtil;

    public void createMember(MemberVO memberVO) {
        Member member = new Member();
        try {
            member.setBirthDate(DateUtils.parseDate(memberVO.getBirthDate(), "dd/MM/yyyy"));
        } catch (ParseException e) {
            SystemException.wrap(e);
        }
        member.setMemberId(memberVO.getMemberId());
        member.setEmailId(memberVO.getEmail());
        member.setFirstName(memberVO.getFirstName());
        member.setMiddleName(memberVO.getMiddleName());
        member.setLastName(memberVO.getLastName());
        member.setGenderId(ApplicationCode.GENDER.getCodeId());
        member.setGenderVal(memberVO.getGender());
        member.setPhoneNo(memberVO.getMobileNo());
        member.setTitleId(ApplicationCode.TITLE.getCodeId());
        member.setTitleVal(memberVO.getTitle());
        member.setLastUpdatedDate(new Date());
        member.setOccupation(memberVO.getOccupation());
        member.setEmailSubscribe(memberVO.getSubscribe());
        member.setRoomNo(memberVO.getRoomNo());

        member = memberRepository.saveAndFlush(member);

        Set<MemberAddress> memberAddressSet = new HashSet<>();
        MemberAddress memberAddress = new MemberAddress();
        MemberAddressPK memberAddressPK = new MemberAddressPK();
        memberAddressPK.setMemberId(member.getMemberId());
        memberAddressPK.setAddressTypeVal(1);
        memberAddress.setId(memberAddressPK);
        memberAddress.setAddress1(memberVO.getAddress1());
        memberAddress.setAddress2(memberVO.getAddress2());
        memberAddress.setAddressTypeId(ApplicationCode.ADDRESS_TYPE.getCodeId());
        memberAddress.setCountryId(ApplicationCode.COUNTRY.getCodeId());
        memberAddress.setCountryVal(memberVO.getCountry());
        memberAddress.setStateId(ApplicationCode.STATE.getCodeId());
        memberAddress.setStateVal(memberVO.getState());
        memberAddress.setCity(memberVO.getCity());
        memberAddress.setPinCode(memberVO.getZip());
        memberAddress.setLastUpdatedDate(new Date());
        memberAddress.setMember(member);
        memberAddressSet.add(memberAddress);
        member.setMemberAddress(memberAddressSet);
        memberRepository.save(member);
    }

    public Member findMemberDetails(Integer memberId) {
        Member member = new Member();
        try {
            member = memberRepository.findByMemberId(memberId);
        } catch (Exception e) {
        }
        return member;
    }

    public Member findMemberByEmailId(String email) {
        return memberRepository.findByEmailId(email);
    }

    public List<Member> findAllMembers() {
        return memberRepository.findAllByOrderByMemberIdAsc();
    }

    @Override
    public void removeMember(Integer memberId) {
        memberRepository.delete(memberId);
    }
}

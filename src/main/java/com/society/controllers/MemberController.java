package com.society.controllers;

import com.society.bo.MemberDTO;
import com.society.config.validate.ApiError;
import com.society.config.validate.MemberValidator;
import com.society.entities.member.Member;
import com.society.service.MemberService;
import com.society.vo.MemberVO;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class MemberController {
    @Autowired
    MemberValidator memberValidator;
    @Autowired
    private MemberService memberService;
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = "/addMember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApiError> createMember(@ModelAttribute(value = "memberVO") MemberVO memberVO, BindingResult result) {
        memberValidator.validate(memberVO, result);
        if (result.hasErrors()) {
            return new ResponseEntity(new ApiError(result),
                    HttpStatus.BAD_REQUEST);
        }
        memberService.createMember(memberVO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/removeMember", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApiError> removeMember(@ModelAttribute(value = "memberVO") MemberVO memberVO) {
        Member member = memberService.findMemberDetails(memberVO.getMemberId());
        if (member == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        memberService.removeMember(memberVO.getMemberId());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/fetchMembers", method = RequestMethod.GET)
    @ResponseBody
    public List<MemberDTO> fetchMembers() {
        List<MemberDTO> memberDTOList = new ArrayList<>();
        List<Member> memberVOList = memberService.findAllMembers();
        for (Member member : memberVOList) {
            MemberDTO memberDTO = new MemberDTO();
            memberDTO.setMemberId(member.getMemberId());
            memberDTO.setName(member.getFirstName().concat(" ").concat(member.getLastName()));
            memberDTO.setRoomNo(member.getRoomNo());
            memberDTO.setOccupation(member.getOccupation());
            memberDTO.setEmail(member.getEmailId());
            memberDTO.setMobileNo(member.getPhoneNo());
            memberDTO.setDateOfBirth(DateFormatUtils.format(member.getBirthDate(), "dd/MM/yyyy"));
            memberDTOList.add(memberDTO);
        }
        return memberDTOList;
    }

    @RequestMapping(value = "/fetchMembers/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public Member fetchMembersById(@PathVariable String memberId) {
        return memberService.findMemberDetails(Integer.parseInt(memberId));
    }

    @RequestMapping(value = "/getTotalMembers", method = RequestMethod.GET)
    @ResponseBody
    public Integer getTotalMembers() {
        return memberService.findAllMembers().size();
    }
}

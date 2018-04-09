package com.society.config.validate;

import com.society.config.exception.ValidationCode;
import com.society.service.MemberServiceImpl;
import com.society.vo.MemberVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class MemberValidator implements Validator {

    @Autowired
    MemberServiceImpl memberService;

    @Override
    public boolean supports(Class<?> aClass) {
        return MemberVO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        MemberVO memberVO = (MemberVO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", CustomValidator.getMessage(ValidationCode.EMAIL_IS_REQUIRED));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "mobileNo", CustomValidator.getMessage(ValidationCode.PHONE_NO_IS_REQUIRED));

        if (memberVO.getMemberId() != null) {
            if (!memberVO.getEmail().equalsIgnoreCase(memberService.findMemberDetails(memberVO.getMemberId()).getEmailId())) {
                if (memberService.findMemberByEmailId(memberVO.getEmail()) != null) {
                    errors.rejectValue("email", CustomValidator.getMessage(ValidationCode.EMAIL_IS_ALREADY_TAKEN));
                }
            }
        } else {
            if (memberService.findMemberByEmailId(memberVO.getEmail()) != null) {
                errors.rejectValue("email", CustomValidator.getMessage(ValidationCode.EMAIL_IS_ALREADY_TAKEN));
            }
        }
    }
}

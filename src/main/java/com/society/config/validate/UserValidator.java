package com.society.config.validate;

import com.society.config.exception.ValidationCode;
import com.society.service.UserService;
import com.society.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidator extends CustomValidator implements Validator {
    @Autowired
    private UserService userService;

    @Override
    public boolean supports(Class<?> aClass) {
        return UserVO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        UserVO userVO = (UserVO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "userName", getMessage(ValidationCode.USER_NAME_IS_REQUIRED));

        if (userVO.getUserName().length() < 5 || userVO.getUserName().length() > 20) {
            errors.rejectValue("userName", getMessage(ValidationCode.USER_NAME_SIZE));
        }
        if (userService.findByUserName(userVO.getUserName()) != null) {
            errors.rejectValue("userName", getMessage(ValidationCode.USER_NAME_EXIST));
        }
        if (userService.findByUserName(userVO.getEmailId()) != null) {
            errors.rejectValue("emailId", getMessage(ValidationCode.EMAIL_IS_REQUIRED));
        }
        if (userService.findAllUsers().size() > 1) {
            errors.rejectValue("userName", getMessage(ValidationCode.REGISTRATION_NOT_ALLOWED));
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", getMessage(ValidationCode.PASSWORD_IS_REQUIRED));
        if (userVO.getPassword().length() < 5 || userVO.getPassword().length() > 32) {
            errors.rejectValue("password", getMessage(ValidationCode.PASSWORD_SIZE));
        }

        if (!userVO.getConfirmPassword().equals(userVO.getPassword())) {
            errors.rejectValue("passwordConfirm", "Diff.userForm.passwordConfirm");
        }
    }
}
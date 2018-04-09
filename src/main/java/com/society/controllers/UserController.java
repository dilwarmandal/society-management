package com.society.controllers;

import com.society.config.exception.ErrorHandler;
import com.society.config.exception.SystemException;
import com.society.config.exception.TransactionCode;
import com.society.config.validate.ApiError;
import com.society.config.validate.UserValidator;
import com.society.entities.user.AppUser;
import com.society.service.UserService;
import com.society.service.email.EmailService;
import com.society.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class UserController {
    @Autowired
    UserValidator userValidator;
    @Autowired
    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage() {
        if (isCurrentAuthenticationAnonymous()) {
            return "login";
        } else {
            return "redirect:/home";
        }
    }

    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String registerUser() {
        return "register";
    }

    @RequestMapping(value = "/failure")
    private ResponseEntity<ErrorHandler> loginFailure() {
        ErrorHandler errorHandler = ErrorHandler.getInstance();
        errorHandler.setErrorCode(TransactionCode.UNAUTHORIZED_ACCESS.getCodeId());
        errorHandler.setErrorDesc(SystemException.getErrorText(TransactionCode.UNAUTHORIZED_ACCESS));
        return new ResponseEntity<>(errorHandler, HttpStatus.UNAUTHORIZED);
    }

    @RequestMapping(value = "/success")
    private ResponseEntity<ErrorHandler> loginSuccess() {
        ErrorHandler errorHandler = ErrorHandler.getInstance();
        errorHandler.setErrorCode(TransactionCode.SUCCESS_LOGIN.getCodeId());
        errorHandler.setErrorDesc(SystemException.getErrorText(TransactionCode.SUCCESS_LOGIN));
        return new ResponseEntity<>(errorHandler, HttpStatus.OK);
    }

    @RequestMapping(value = "/doRegister", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApiError> registerUser(@ModelAttribute(value = "userVO") UserVO userVO, BindingResult result) {
        userValidator.validate(userVO, result);
        if (result.hasErrors()) {
            return new ResponseEntity(new ApiError(result),
                    HttpStatus.BAD_REQUEST);
        }
        userService.registerUser(userVO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApiError> forgotPassword(@RequestParam("email") String userEmail, HttpServletRequest httpServletRequest) {
        AppUser appUser = userService.findUserByEmail(userEmail);
        if (appUser == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(appUser, token);
        emailService.resetPassword(getAppUrl(httpServletRequest), token, appUser);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            persistentTokenBasedRememberMeServices.logout(request, response, auth);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }

    private boolean isCurrentAuthenticationAnonymous() {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }
}

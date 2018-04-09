package com.society.controllers;

import com.society.bo.SystemCodesBO;
import com.society.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by Dilwar on 17-12-2016.
 */
@Controller
public class ApplicationController {

    @Autowired
    private
    UtilityService utilityService;

    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public String loginUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/home";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = {"/home"}, method = RequestMethod.GET)
    public String home() {
        return "home";
    }

    @RequestMapping(value = {"/member"}, method = RequestMethod.GET)
    public String member() {
        return "member";
    }

    @RequestMapping(value = {"/account"}, method = RequestMethod.GET)
    public String account() {
        return "account";
    }

    @RequestMapping(value = {"/report"}, method = RequestMethod.GET)
    public String report() {
        return "report";
    }

    @RequestMapping(value = {"/forgot-password"}, method = RequestMethod.GET)
    public String forgotPassword() {
        return "forgot-password";
    }

    @RequestMapping(value = "/getDropDownList/{codeIdRef}/{refNo}", method = RequestMethod.GET)
    public
    @ResponseBody
    Object getDropDownList(@PathVariable String codeIdRef,
                           @PathVariable String refNo) {
        SystemCodesBO systemCodesBO = new SystemCodesBO();
        systemCodesBO.setCodeDesc(codeIdRef);
        if (!refNo.equals("0")) {
            systemCodesBO.setRefId(Integer.parseInt(refNo));
        }
        return utilityService.getDropDownList(systemCodesBO);
    }
}

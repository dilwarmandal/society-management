package com.society.config.security;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class ApplicationUtil {

    public static ApplicationUtil instance = null;

    public static ApplicationUtil getInstance() {
        if (instance == null) {
            instance = new ApplicationUtil();
        }
        return instance;
    }

    public String getPrincipal() {
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            userName = ((UserDetails) principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }
}

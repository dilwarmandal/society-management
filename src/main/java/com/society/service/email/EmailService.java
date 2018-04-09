package com.society.service.email;

import com.society.entities.user.AppUser;

public interface EmailService {

    void resetPassword(String contextPath, String token, AppUser appUser);

    void sentMonthlyStatement();


}

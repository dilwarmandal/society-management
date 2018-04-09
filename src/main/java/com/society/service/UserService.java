package com.society.service;


import com.society.entities.user.AppUser;
import com.society.vo.UserVO;

import java.util.List;
import java.util.Map;

public interface UserService {

    void registerUser(UserVO userVO);

    Map<String, Object> loginUser(UserVO userVO);

    AppUser findByUserId(int id);

    AppUser findByUserName(String userName);

    AppUser findUserByEmail(String email);

    List<AppUser> findAllUsers();

    void createPasswordResetTokenForUser(AppUser appUser, String token);
}

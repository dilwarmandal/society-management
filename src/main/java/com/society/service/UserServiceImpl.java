package com.society.service;

import com.society.config.exception.ApplicationCode;
import com.society.entities.user.AppUser;
import com.society.entities.user.PasswordResetToken;
import com.society.entities.user.UserRole;
import com.society.entities.user.UserRolePK;
import com.society.repositories.PasswordTokenRepository;
import com.society.repositories.UserRepository;
import com.society.util.HibernateUtil;
import com.society.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    HibernateUtil hibernateUtil;
    @Autowired
    PasswordTokenRepository passwordTokenRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @PersistenceContext
    private EntityManager entityManager;

    public void registerUser(UserVO userVO) {
        Map<String, Object> finalMap = new HashMap<>();

        AppUser appUser = new AppUser();
        appUser.setUserName(userVO.getUserName());
        appUser.setEmail(userVO.getEmailId());
        appUser.setPassword(passwordEncoder.encode(userVO.getPassword()));
        appUser.setUserStatusId(ApplicationCode.USER_STATUS.getCodeId());
        appUser.setUserStatusVal(1);
        appUser.setUserTypeId(ApplicationCode.USER_TYPE.getCodeId());
        appUser.setUserTypeVal(1);
        appUser.setUserCreatedDate(new Date());
        appUser = userRepository.saveAndFlush(appUser);

        UserRole userRoleAdmin = new UserRole();
        userRoleAdmin.setAppUser(appUser);

        UserRolePK userRolePKAdmin = new UserRolePK();
        userRolePKAdmin.setUserId(appUser.getId());
        userRolePKAdmin.setRoleVal(1);

        userRoleAdmin.setId(userRolePKAdmin);
        userRoleAdmin.setAppUser(appUser);
        userRoleAdmin.setRoleId(ApplicationCode.USER_ROLE.getCodeId());
        userRoleAdmin.setLastUpdatedDate(new Date());

        UserRole userRoleUser = new UserRole();
        userRoleUser.setAppUser(appUser);

        UserRolePK userRolePKUser = new UserRolePK();
        userRolePKUser.setUserId(appUser.getId());
        userRolePKUser.setRoleVal(2);

        userRoleUser.setId(userRolePKUser);
        userRoleUser.setAppUser(appUser);
        userRoleUser.setRoleId(ApplicationCode.USER_ROLE.getCodeId());
        userRoleUser.setLastUpdatedDate(new Date());
        Set<UserRole> userRoleSet = new HashSet<>();
        userRoleSet.add(userRoleUser);
        userRoleSet.add(userRoleAdmin);
        appUser.setUserRoles(userRoleSet);
        userRepository.save(appUser);
    }

    public Map<String, Object> loginUser(UserVO userVO) {
        return null;
    }

    public AppUser findByUserId(int userId) {
        return userRepository.findById(userId);
    }

    public AppUser findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public AppUser findUserByEmail(String email) {
        return userRepository.findAppUserByEmail(email);
    }

    public List<AppUser> findAllUsers() {
        return userRepository.findAllByOrderByIdAsc();
    }

    @Override
    public void createPasswordResetTokenForUser(AppUser appUser, String token) {
        final PasswordResetToken myToken = new PasswordResetToken(token, appUser);
        passwordTokenRepository.save(myToken);
    }
}

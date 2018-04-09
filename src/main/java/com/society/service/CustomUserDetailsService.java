package com.society.service;

import com.society.bo.SystemCodesBO;
import com.society.config.exception.TransactionCode;
import com.society.config.validate.CustomValidator;
import com.society.entities.system.SystemCodesVal;
import com.society.entities.user.AppUser;
import com.society.entities.user.UserRole;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private UserService userService;

    @Autowired
    private SessionFactory sessionFactory;

    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String userName)
            throws UsernameNotFoundException {
        AppUser user = userService.findByUserName(userName);
        if (user == null) {
            throw new UsernameNotFoundException(CustomValidator.getMessage(TransactionCode.USER_NOT_FOUND));
        }
        return new User(user.getUserName(), user.getPassword(),
                (user.getUserStatusVal() == 1), true, true, true, getGrantedAuthorities(user));
    }

    private List<GrantedAuthority> getGrantedAuthorities(AppUser user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : user.getUserRoles()) {
            SystemCodesBO systemCodesBO = new SystemCodesBO();
            systemCodesBO.setCodeId(userRole.getRoleId());
            systemCodesBO.setCodeVal(userRole.getId().getRoleVal());
            List<SystemCodesVal> systemCodesValList = getSystemCodeValList(systemCodesBO);
            if (systemCodesValList != null && !systemCodesValList.isEmpty()) {
                systemCodesValList.forEach(item ->
                        authorities.add(new SimpleGrantedAuthority(item.getCodeSortDesc()))
                );
            }
        }
        return authorities;
    }

    private List<SystemCodesVal> getSystemCodeValList(SystemCodesBO systemCodesBO) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(SystemCodesVal.class);
        criteria.add(Restrictions.eq("id.codeId", systemCodesBO.getCodeId()));
        if (systemCodesBO.getCodeVal() > 0) {
            criteria.add(Restrictions.eq("id.codeVal", systemCodesBO.getCodeVal()));
        }
        return criteria.list();
    }
}

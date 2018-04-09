/*
 * Copyright (c) 2016. Dilwar Mandal - SocialAPP
 */

package com.society.config.security;

/**
 * Created by Dilwar on 18-08-2016.
 */

import com.society.entities.user.PersistentLogin;
import com.society.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Repository("tokenRepositoryDao")
@Transactional
public class TokenRepositoryImpl
        implements PersistentTokenRepository {
    static final Logger logger = LoggerFactory.getLogger(TokenRepositoryImpl.class);
    @Autowired
    HibernateUtil hibernateUtil;

    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        logger.info("Creating Token for user : {}", token.getUsername());
        PersistentLogin persistentLogin = new PersistentLogin();
        persistentLogin.setUserName(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUpdatedDate(token.getDate());
        hibernateUtil.persist(persistentLogin);

    }

    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        logger.info("Fetch Token if any for seriesId : {}", seriesId);
        try {
            Criteria crit = hibernateUtil.createEntityCriteria(PersistentLogin.class);
            crit.add(Restrictions.eq("series", seriesId));
            PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();

            return new PersistentRememberMeToken(persistentLogin.getUserName(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLastUpdatedDate());
        } catch (Exception e) {
            logger.info("Token not found...");
            return null;
        }
    }

    @Override
    public void removeUserTokens(String username) {
        logger.info("Removing Token if any for user : {}", username);
        Criteria crit = hibernateUtil.createEntityCriteria(PersistentLogin.class);
        crit.add(Restrictions.eq("userName", username));
        PersistentLogin persistentLogin = (PersistentLogin) crit.uniqueResult();
        if (persistentLogin != null) {
            logger.info("rememberMe was selected");
            hibernateUtil.delete(persistentLogin);
        }

    }

    @Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        logger.info("Updating Token for seriesId : {}", seriesId);
        PersistentLogin persistentLogin = hibernateUtil.fetchById(PersistentLogin.class, seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUpdatedDate(lastUsed);
        hibernateUtil.update(persistentLogin);
    }

}


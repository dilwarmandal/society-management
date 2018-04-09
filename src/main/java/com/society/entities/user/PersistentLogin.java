/*
 * Copyright (c) 2016. Dilwar Mandal - SocialAPP
 */

package com.society.entities.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "PERSISTENT_LOGINS")
public class PersistentLogin implements Serializable {

    @Id
    private String series;

    @Column(name = "USER_NAME", unique = true, nullable = false)
    private String userName;

    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdatedDate;

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }


}

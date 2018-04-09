package com.society.entities.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the app_user database table.
 */
@Entity
@Table(name = "app_user")
@NamedQuery(name = "AppUser.findAll", query = "SELECT a FROM AppUser a")
public class AppUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Integer id;

    @Column(name = "USER_NAME", unique = true)
    private String userName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGIN_DATE")
    private Date lastLoginDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_LOGOUT_DATE")
    private Date lastLogoutDate;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "EMAIL", unique = true)
    private String email;

    @Lob
    @Column(name = "PROFILE_IMAGE")
    private byte[] profileImage;

    @Column(name = "SESSION_TOKEN")
    private String sessionToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "USER_CREATED_DATE")
    private Date userCreatedDate;

    @Column(name = "USER_STATUS_ID")
    private int userStatusId;

    @Column(name = "USER_STATUS_VAL")
    private int userStatusVal;

    @Column(name = "USER_TYPE_ID")
    private int userTypeId;

    @Column(name = "USER_TYPE_VAL")
    private int userTypeVal;

    //bi-directional many-to-one association to UserRole
    @OneToMany(mappedBy = "appUser", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Date getLastLogoutDate() {
        return lastLogoutDate;
    }

    public void setLastLogoutDate(Date lastLogoutDate) {
        this.lastLogoutDate = lastLogoutDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }

    public Date getUserCreatedDate() {
        return userCreatedDate;
    }

    public void setUserCreatedDate(Date userCreatedDate) {
        this.userCreatedDate = userCreatedDate;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserStatusId() {
        return userStatusId;
    }

    public void setUserStatusId(int userStatusId) {
        this.userStatusId = userStatusId;
    }

    public int getUserStatusVal() {
        return userStatusVal;
    }

    public void setUserStatusVal(int userStatusVal) {
        this.userStatusVal = userStatusVal;
    }

    public int getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(int userTypeId) {
        this.userTypeId = userTypeId;
    }

    public int getUserTypeVal() {
        return userTypeVal;
    }

    public void setUserTypeVal(int userTypeVal) {
        this.userTypeVal = userTypeVal;
    }


    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
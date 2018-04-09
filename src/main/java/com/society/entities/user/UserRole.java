package com.society.entities.user;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;


/**
 * The persistent class for the user_roles database table.
 */
@Entity
@Table(name = "user_roles")
@NamedQuery(name = "UserRole.findAll", query = "SELECT u FROM UserRole u")
public class UserRole implements Serializable {
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private UserRolePK id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "ROLE_ID")
    private int roleId;

    //bi-directional many-to-one association to AppUser
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private AppUser appUser;

    public UserRole() {
    }

    public UserRolePK getId() {
        return this.id;
    }

    public void setId(UserRolePK id) {
        this.id = id;
    }

    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getRoleId() {
        return this.roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public AppUser getAppUser() {
        return this.appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

}
package com.society.entities.user;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the user_roles database table.
 */
@Embeddable
public class UserRolePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "USER_ID", insertable = false, updatable = false)
    private Integer userId;

    @Column(name = "ROLE_VAL")
    private Integer roleVal;

    public UserRolePK() {
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getRoleVal() {
        return this.roleVal;
    }

    public void setRoleVal(Integer roleVal) {
        this.roleVal = roleVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserRolePK that = (UserRolePK) o;

        if (userId != null ? !userId.equals(that.userId) : that.userId != null) return false;
        return roleVal != null ? roleVal.equals(that.roleVal) : that.roleVal == null;
    }

    @Override
    public int hashCode() {
        int result = userId != null ? userId.hashCode() : 0;
        result = 31 * result + (roleVal != null ? roleVal.hashCode() : 0);
        return result;
    }
}
package com.society.entities.member;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the user_address database table.
 */
@Embeddable
public class MemberAddressPK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "MEMBER_ID")
    private Integer memberId;

    @Column(name = "ADDRESS_TYPE_VAL")
    private Integer addressTypeVal;

    public MemberAddressPK() {
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int memberId) {
        this.memberId = memberId;
    }

    public int getAddressTypeVal() {
        return this.addressTypeVal;
    }

    public void setAddressTypeVal(int addressTypeVal) {
        this.addressTypeVal = addressTypeVal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MemberAddressPK that = (MemberAddressPK) o;

        if (memberId != null ? !memberId.equals(that.memberId) : that.memberId != null) return false;
        return addressTypeVal != null ? addressTypeVal.equals(that.addressTypeVal) : that.addressTypeVal == null;
    }

    @Override
    public int hashCode() {
        int result = memberId != null ? memberId.hashCode() : 0;
        result = 31 * result + (addressTypeVal != null ? addressTypeVal.hashCode() : 0);
        return result;
    }
}
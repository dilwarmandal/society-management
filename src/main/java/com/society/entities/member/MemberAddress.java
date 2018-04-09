package com.society.entities.member;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "member_address")
@NamedQuery(name = "MemberAddress.findAll", query = "SELECT u FROM MemberAddress u")
public class MemberAddress {

    private static final long serialVersionUID = 1L;

    @EmbeddedId
    private MemberAddressPK id;

    @Column(name = "ADDRESS_1")
    private String address1;

    @Column(name = "ADDRESS_2")
    private String address2;

    @Column(name = "ADDRESS_TYPE_ID")
    private int addressTypeId;

    @Column(name = "CITY")
    private String city;

    @Column(name = "COUNTRY_ID")
    private int countryId;

    @Column(name = "COUNTRY_VAL")
    private int countryVal;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "PIN_CODE")
    private int pinCode;

    @Column(name = "STATE_ID")
    private int stateId;

    @Column(name = "STATE_VAL")
    private int stateVal;

    //bi-directional many-to-one association to Member
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID", insertable = false, updatable = false)
    @JsonBackReference
    private Member member;

    public MemberAddress() {
    }

    public MemberAddressPK getId() {
        return this.id;
    }

    public void setId(MemberAddressPK id) {
        this.id = id;
    }

    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public int getAddressTypeId() {
        return this.addressTypeId;
    }

    public void setAddressTypeId(int addressTypeId) {
        this.addressTypeId = addressTypeId;
    }

    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getCountryId() {
        return this.countryId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public int getCountryVal() {
        return this.countryVal;
    }

    public void setCountryVal(int countryVal) {
        this.countryVal = countryVal;
    }

    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public int getPinCode() {
        return this.pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }

    public int getStateId() {
        return this.stateId;
    }

    public void setStateId(int stateId) {
        this.stateId = stateId;
    }

    public int getStateVal() {
        return this.stateVal;
    }

    public void setStateVal(int stateVal) {
        this.stateVal = stateVal;
    }

    public Member getMember() {
        return this.member;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}

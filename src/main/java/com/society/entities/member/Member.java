package com.society.entities.member;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * The persistent class for the member database table.
 */
@Entity
@Table(name = "member")
@NamedQuery(name = "Member.findAll", query = "SELECT u FROM Member u")
public class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "MEMBER_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer memberId;

    @Column(name = "BIRTH_DATE")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
    private Date birthDate;

    @Column(name = "EMAIL_ID", unique = true)
    private String emailId;

    @Column(name = "FIRST_NAME")
    private String firstName;

    @Column(name = "GENDER_ID")
    private Integer genderId;

    @Column(name = "GENDER_VAL")
    private Integer genderVal;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    @Column(name = "MIDDLE_NAME")
    private String middleName;

    @Column(name = "PHONE_NO")
    private String phoneNo;

    @Column(name = "TITLE_ID")
    private Integer titleId;

    @Column(name = "TITLE_VAL")
    private Integer titleVal;

    @Column(name = "ROOM_NO")
    private String roomNo;

    @Column(name = "OCCUPATION")
    private String occupation;

    @Column(name = "EMAIL_SUBSCRIBE")
    private Boolean isEmailSubscribe;

    //bi-directional many-to-one association to MemberAddress
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    @JsonManagedReference
    private Set<MemberAddress> memberAddress;

    public Member() {
    }

    public Integer getMemberId() {
        return this.memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public Date getBirthDate() {
        return this.birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailId() {
        return this.emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Integer getGenderId() {
        return this.genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public Integer getGenderVal() {
        return this.genderVal;
    }

    public void setGenderVal(Integer genderVal) {
        this.genderVal = genderVal;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getLastUpdatedDate() {
        return this.lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getMiddleName() {
        return this.middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPhoneNo() {
        return this.phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Integer getTitleId() {
        return this.titleId;
    }

    public void setTitleId(Integer titleId) {
        this.titleId = titleId;
    }

    public Integer getTitleVal() {
        return this.titleVal;
    }

    public void setTitleVal(Integer titleVal) {
        this.titleVal = titleVal;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public Boolean getEmailSubscribe() {
        return isEmailSubscribe;
    }

    public void setEmailSubscribe(Boolean emailSubscribe) {
        isEmailSubscribe = emailSubscribe;
    }

    public Set<MemberAddress> getMemberAddress() {
        return memberAddress;
    }

    public void setMemberAddress(Set<MemberAddress> memberAddress) {
        this.memberAddress = memberAddress;
    }

}
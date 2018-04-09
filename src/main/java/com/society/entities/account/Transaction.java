package com.society.entities.account;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "transaction")
@NamedQuery(name = "Transaction.findAll", query = "SELECT u FROM Transaction u")
public class Transaction implements Serializable {

    @Id
    @Column(name = "TRANS_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer transId;

    @Column(name = "TRANS_REF", unique = false, nullable = false)
    private String transRef;

    @Column(name = "TRANS_MODE_ID", nullable = false)
    private Integer transModeId;

    @Column(name = "TRANS_MODE_VAL", nullable = false)
    private Integer transModeVal;

    @Column(name = "TRANS_SELECT_ID", nullable = false)
    private Integer transSelectId;

    @Column(name = "TRANS_SELECT_VAL", nullable = false)
    private Integer transSelectVal;

    @Column(name = "TRANS_TYPE_ID", nullable = false)
    private Integer transTypeId;

    @Column(name = "TRANS_TYPE_VAL", nullable = false)
    private Integer transTypeVal;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "IST")
    @Column(name = "TRANS_DATE", nullable = false)
    private Date transDate;

    @Column(name = "TRANS_AMOUNT", nullable = false)
    private BigDecimal transAmount;

    @Column(name = "TRANS_DESC")
    private String transDesc;

    @Column(name = "TRANS_STATUS")
    private Integer transStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_UPDATED_DATE")
    private Date lastUpdatedDate;

    public Integer getTransId() {
        return transId;
    }

    public void setTransId(Integer transId) {
        this.transId = transId;
    }

    public String getTransRef() {
        return transRef;
    }

    public void setTransRef(String transRef) {
        this.transRef = transRef;
    }

    public Integer getTransModeId() {
        return transModeId;
    }

    public void setTransModeId(Integer transModeId) {
        this.transModeId = transModeId;
    }

    public Integer getTransModeVal() {
        return transModeVal;
    }

    public void setTransModeVal(Integer transModeVal) {
        this.transModeVal = transModeVal;
    }

    public Integer getTransSelectId() {
        return transSelectId;
    }

    public void setTransSelectId(Integer transSelectId) {
        this.transSelectId = transSelectId;
    }

    public Integer getTransSelectVal() {
        return transSelectVal;
    }

    public void setTransSelectVal(Integer transSelectVal) {
        this.transSelectVal = transSelectVal;
    }

    public Integer getTransTypeId() {
        return transTypeId;
    }

    public void setTransTypeId(Integer transTypeId) {
        this.transTypeId = transTypeId;
    }

    public Integer getTransTypeVal() {
        return transTypeVal;
    }

    public void setTransTypeVal(Integer transTypeVal) {
        this.transTypeVal = transTypeVal;
    }

    public Date getTransDate() {
        return transDate;
    }

    public void setTransDate(Date transDate) {
        this.transDate = transDate;
    }

    public BigDecimal getTransAmount() {
        return transAmount;
    }

    public void setTransAmount(BigDecimal transAmount) {
        this.transAmount = transAmount;
    }

    public String getTransDesc() {
        return transDesc;
    }

    public void setTransDesc(String transDesc) {
        this.transDesc = transDesc;
    }

    public Integer getTransStatus() {
        return transStatus;
    }

    public void setTransStatus(Integer transStatus) {
        this.transStatus = transStatus;
    }

    public Date getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(Date lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }
}
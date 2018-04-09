package com.society.vo;

import java.math.BigDecimal;

public class TransactionVO {
    Integer transMode;
    Integer transSelect;
    Integer transType;
    String transDate;
    BigDecimal amount;
    String description;
    Integer transactionId;

    public Integer getTransMode() {
        return transMode;
    }

    public void setTransMode(Integer transMode) {
        this.transMode = transMode;
    }

    public Integer getTransSelect() {
        return transSelect;
    }

    public void setTransSelect(Integer transSelect) {
        this.transSelect = transSelect;
    }

    public Integer getTransType() {
        return transType;
    }

    public void setTransType(Integer transType) {
        this.transType = transType;
    }

    public String getTransDate() {
        return transDate;
    }

    public void setTransDate(String transDate) {
        this.transDate = transDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }
}

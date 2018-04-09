package com.society.bo;

import java.math.BigDecimal;

public class AccountInfoDTO {
    BigDecimal totalCreditAmount;
    BigDecimal totalDebitAmount;
    BigDecimal totalCashInBank;
    BigDecimal totalCashInHand;
    Object[] monthlyCreditFlow;
    Object[] monthlyDebitFlow;
    Object[] monthlyCashFlow;
    Integer currentFiscal;

    public BigDecimal getTotalCreditAmount() {
        return totalCreditAmount;
    }

    public void setTotalCreditAmount(BigDecimal totalCreditAmount) {
        this.totalCreditAmount = totalCreditAmount;
    }

    public BigDecimal getTotalDebitAmount() {
        return totalDebitAmount;
    }

    public void setTotalDebitAmount(BigDecimal totalDebitAmount) {
        this.totalDebitAmount = totalDebitAmount;
    }

    public BigDecimal getTotalCashInBank() {
        return totalCashInBank;
    }

    public void setTotalCashInBank(BigDecimal totalCashInBank) {
        this.totalCashInBank = totalCashInBank;
    }

    public BigDecimal getTotalCashInHand() {
        return totalCashInHand;
    }

    public void setTotalCashInHand(BigDecimal totalCashInHand) {
        this.totalCashInHand = totalCashInHand;
    }

    public Object[] getMonthlyCreditFlow() {
        return monthlyCreditFlow;
    }

    public void setMonthlyCreditFlow(Object[] monthlyCreditFlow) {
        this.monthlyCreditFlow = monthlyCreditFlow;
    }

    public Object[] getMonthlyDebitFlow() {
        return monthlyDebitFlow;
    }

    public void setMonthlyDebitFlow(Object[] monthlyDebitFlow) {
        this.monthlyDebitFlow = monthlyDebitFlow;
    }

    public Object[] getMonthlyCashFlow() {
        return monthlyCashFlow;
    }

    public void setMonthlyCashFlow(Object[] monthlyCashFlow) {
        this.monthlyCashFlow = monthlyCashFlow;
    }

    public Integer getCurrentFiscal() {
        return currentFiscal;
    }

    public void setCurrentFiscal(Integer currentFiscal) {
        this.currentFiscal = currentFiscal;
    }
}

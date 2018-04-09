package com.society.dao;

import java.math.BigDecimal;
import java.util.Map;

public interface TransactionDao {
    Map<String, BigDecimal> getAccountInformation(Integer transactionId);

    Object[] getMonthlyTransactionByType(Integer transType);

    Object[] getMonthTransactionAmount();

}

package com.society.service;


import com.society.bo.AccountInfoDTO;
import com.society.bo.TransactionDTO;
import com.society.entities.account.Transaction;
import com.society.vo.TransactionVO;

import java.util.Date;
import java.util.List;

public interface TransactionService {

    void createTransaction(TransactionVO userVO);

    Transaction findTransactionDetails(int transactionId);

    List<TransactionDTO> findAllTransactions();

    List<TransactionDTO> findAllTransactionsBetweenDate(Date fromDate, Date toDate);

    AccountInfoDTO getAccountInformationService(Integer transactionId);

    void removeTransaction(Integer transactionId);
}

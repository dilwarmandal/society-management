package com.society.service;

import com.society.bo.AccountInfoDTO;
import com.society.bo.TransactionDTO;
import com.society.config.exception.ApplicationCode;
import com.society.config.exception.SystemException;
import com.society.dao.TransactionDao;
import com.society.dao.UtilityDao;
import com.society.entities.account.Transaction;
import com.society.repositories.TransactionRepository;
import com.society.util.HibernateUtil;
import com.society.vo.TransactionVO;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service("transactionService")
@Transactional
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    HibernateUtil hibernateUtil;

    @Autowired
    UtilityDao utilityDao;

    @Autowired
    TransactionDao transactionDao;

    @Override
    public void createTransaction(TransactionVO transactionVO) {
        Transaction transaction = new Transaction();
        if (transactionVO.getTransactionId() != null) {
            Transaction transactionUpdate = transactionRepository.findByTransId(transactionVO.getTransactionId());
            transactionUpdate.setTransStatus(2); // 2=Deactivate
            transactionUpdate.setLastUpdatedDate(new Date());
            transactionRepository.saveAndFlush(transactionUpdate);
            transaction.setTransRef(transactionUpdate.getTransRef());
            //update
        } else {
            transaction.setTransRef("TXN".concat(RandomStringUtils.random(11, false, true)));
        }
        transaction.setTransAmount(transactionVO.getAmount());
        transaction.setTransDesc(transactionVO.getDescription());
        transaction.setTransModeId(ApplicationCode.TRANSACTION_MODES.getCodeId());
        transaction.setTransModeVal(transactionVO.getTransMode());
        transaction.setTransTypeId(ApplicationCode.TRANSACTION_TYPES.getCodeId());
        transaction.setTransTypeVal(transactionVO.getTransType());
        transaction.setTransStatus(1); // 1=Active
        transaction.setLastUpdatedDate(new Date());
        if (transactionVO.getTransType() == 1) {
            transaction.setTransSelectId(ApplicationCode.TRANSACTION_CREDIT_SELECTION.getCodeId());
        }
        if (transactionVO.getTransType() == 2) {
            transaction.setTransSelectId(ApplicationCode.TRANSACTION_DEBIT_SELECTION.getCodeId());
        }
        transaction.setTransSelectVal(transactionVO.getTransSelect());
        try {
            transaction.setTransDate(DateUtils.parseDate(transactionVO.getTransDate() + new SimpleDateFormat("HH:mm:ss").format(new Date()),
                    "dd/MM/yyyyHH:mm:ss"));
        } catch (ParseException e) {
            SystemException.wrap(e);
        }
        transactionRepository.saveAndFlush(transaction);
    }

    @Override
    public Transaction findTransactionDetails(int transactionId) {
        return transactionRepository.findByTransId(transactionId);
    }

    @Override
    public List<TransactionDTO> findAllTransactions() {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAllByOrderByTransDateDesc();
        for (Transaction transaction : transactions) {
            if (transaction.getTransStatus() == 1)
                transactionDTOList.add(getTransactionInfo(transaction));
        }
        return transactionDTOList;
    }

    @Override
    public List<TransactionDTO> findAllTransactionsBetweenDate(Date fromDate, Date toDate) {
        List<TransactionDTO> transactionDTOList = new ArrayList<>();
        List<Transaction> transactions = transactionRepository.findAllByOrderByTransDateDesc();
        for (Transaction transaction : transactions) {
            if (transaction.getTransStatus() == 1 && transaction.getTransDate().after(fromDate) && transaction.getTransDate().before(toDate))
                transactionDTOList.add(getTransactionInfo(transaction));
        }
        return transactionDTOList;
    }

    public TransactionDTO getTransactionInfo(Transaction transaction) {
        TransactionDTO transactionDTO = new TransactionDTO();
        transactionDTO.setTransactionId(transaction.getTransId());
        transactionDTO.setAmount(transaction.getTransAmount());
        transactionDTO.setTransMode(utilityDao.getSystemCodesVal(ApplicationCode.TRANSACTION_MODES.getCodeId(), transaction.getTransModeVal()).getCodeDesc());
        if (transaction.getTransTypeVal() == 1) {
            transactionDTO.setTransSelect(utilityDao.getSystemCodesVal(ApplicationCode.TRANSACTION_CREDIT_SELECTION.getCodeId(), transaction.getTransSelectVal()).getCodeDesc());
        }
        if (transaction.getTransTypeVal() == 2) {
            transactionDTO.setTransSelect(utilityDao.getSystemCodesVal(ApplicationCode.TRANSACTION_DEBIT_SELECTION.getCodeId(), transaction.getTransSelectVal()).getCodeDesc());
        }
        transactionDTO.setTransType(utilityDao.getSystemCodesVal(ApplicationCode.TRANSACTION_TYPES.getCodeId(), transaction.getTransTypeVal()).getCodeDesc());
        transactionDTO.setDescription(transaction.getTransDesc());
        transactionDTO.setTransRef(transaction.getTransRef());
        transactionDTO.setTransDate(DateFormatUtils.format(transaction.getTransDate(), "dd/MM/yyyy"));
        return transactionDTO;
    }

    @Override
    public AccountInfoDTO getAccountInformationService(Integer transactionId) {
        AccountInfoDTO accountInfoDTO = new AccountInfoDTO();
        Map<String, BigDecimal> stringBigDecimalMap = transactionDao.getAccountInformation(transactionId);
        accountInfoDTO.setTotalCreditAmount(stringBigDecimalMap.get("credit"));
        accountInfoDTO.setTotalDebitAmount(stringBigDecimalMap.get("debit"));
        accountInfoDTO.setTotalCashInBank(stringBigDecimalMap.get("bank"));
        accountInfoDTO.setTotalCashInHand(stringBigDecimalMap.get("cash"));
        accountInfoDTO.setMonthlyCashFlow(transactionDao.getMonthTransactionAmount());
        accountInfoDTO.setMonthlyCreditFlow(transactionDao.getMonthlyTransactionByType(1));
        accountInfoDTO.setMonthlyDebitFlow(transactionDao.getMonthlyTransactionByType(2));
        LocalDate currentDate = LocalDate.now();
        accountInfoDTO.setCurrentFiscal(currentDate.getYear());
        return accountInfoDTO;
    }

    @Override
    public void removeTransaction(Integer transactionId) {
        transactionRepository.delete(transactionId);
    }
}

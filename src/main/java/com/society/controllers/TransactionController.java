package com.society.controllers;

import com.society.bo.AccountInfoDTO;
import com.society.bo.TransactionDTO;
import com.society.config.validate.ApiError;
import com.society.config.validate.TransactionValidator;
import com.society.entities.account.Transaction;
import com.society.service.TransactionService;
import com.society.vo.TransactionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TransactionController {
    @Autowired
    TransactionValidator transactionValidator;
    @Autowired
    private TransactionService transactionService;

    @RequestMapping(value = "/addTransaction", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApiError> createTransaction(@ModelAttribute(value = "transactionVO") TransactionVO transactionVO, BindingResult result) {
        transactionValidator.validate(transactionVO, result);
        if (result.hasErrors()) {
            return new ResponseEntity(new ApiError(result),
                    HttpStatus.BAD_REQUEST);
        }
        transactionService.createTransaction(transactionVO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/removeTransaction", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<ApiError> removeTransaction(@ModelAttribute(value = "transactionVO") TransactionVO transactionVO) {
        Transaction transaction = transactionService.findTransactionDetails(transactionVO.getTransactionId());
        if (transaction == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        transactionService.removeTransaction(transactionVO.getTransactionId());
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(value = "/fetchTransactions", method = RequestMethod.GET)
    @ResponseBody
    public List<TransactionDTO> fetchTransactions() {
        return transactionService.findAllTransactions();
    }

    @RequestMapping(value = "/fetchTransactions/{transactionId}", method = RequestMethod.GET)
    @ResponseBody
    public Transaction fetchTransactionById(@PathVariable String transactionId) {
        return transactionService.findTransactionDetails(Integer.parseInt(transactionId));
    }

    @RequestMapping(value = "/getAccountInfo", method = RequestMethod.GET)
    @ResponseBody
    public AccountInfoDTO getAccountInfo() {
        return transactionService.getAccountInformationService(null);
    }


}

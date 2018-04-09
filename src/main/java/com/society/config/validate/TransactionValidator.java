package com.society.config.validate;

import com.society.bo.AccountInfoDTO;
import com.society.config.exception.ValidationCode;
import com.society.service.TransactionService;
import com.society.vo.TransactionVO;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

@Component
public class TransactionValidator extends CustomValidator implements Validator {

    @Autowired
    TransactionService transactionService;

    @Override
    public boolean supports(Class<?> aClass) {
        return TransactionVO.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        TransactionVO transactionVO = (TransactionVO) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "transMode", getMessage(ValidationCode.TRANSACTION_MODE_REQUIRED));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "transSelect", getMessage(ValidationCode.TRANSACTION_SELECTION_REQUIRED));

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "transType", getMessage(ValidationCode.TRANSACTION_TYPE_REQUIRED));

        if (transactionVO.getTransType() == 2) {
            AccountInfoDTO accountInfoDTO = transactionService.getAccountInformationService(transactionVO.getTransactionId());
            if (transactionVO.getTransMode() == 1 && (accountInfoDTO.getTotalCashInHand().subtract(transactionVO.getAmount())).compareTo(BigDecimal.ZERO) < 0) {
                errors.rejectValue("amount", CustomValidator.getMessage(ValidationCode.AMOUNT_NOT_AVAILABLE_AS_CASH));
            }
            if (transactionVO.getTransMode() == 2 && (accountInfoDTO.getTotalCashInBank().subtract(transactionVO.getAmount())).compareTo(BigDecimal.ZERO) < 0) {
                errors.rejectValue("amount", CustomValidator.getMessage(ValidationCode.AMOUNT_NOT_AVAILABLE_IN_BANK));
            }
        }
        if (transactionVO.getAmount().compareTo(BigDecimal.ZERO) == 0) {
            errors.rejectValue("amount", CustomValidator.getMessage(ValidationCode.AMOUNT_ZERO_NOT_ALLOWED));
        }
        Date transactionDate = new Date();
        try {
            transactionDate = DateUtils.parseDate(transactionVO.getTransDate(), "dd/MM/yyyy");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (transactionDate.after(new Date())) {
            errors.rejectValue("transDate", CustomValidator.getMessage(ValidationCode.TRANSACTION_DATE_NOT_ALLOWED_AFTER_CURRENT_DATE));
        }
    }
}

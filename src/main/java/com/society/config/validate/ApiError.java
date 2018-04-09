package com.society.config.validate;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.ArrayList;
import java.util.List;

public class ApiError {

    private List<String> message = new ArrayList<>();

    public ApiError(BindingResult bindingResult) {
        super();
        for (Object object : bindingResult.getAllErrors()) {
            if (object instanceof FieldError) {
                FieldError fieldError = (FieldError) object;
                this.message.add(fieldError.getCode());
            }
        }
    }

    public List<String> getMessage() {
        return message;
    }

    public void setMessage(List<String> message) {
        this.message = message;
    }
}
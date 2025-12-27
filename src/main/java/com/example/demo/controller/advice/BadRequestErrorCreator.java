package com.example.demo.controller.advice;

import java.util.stream.Collectors;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.demo.model.BadRequestError;
import com.example.demo.model.InvalidParam;

public class BadRequestErrorCreator {
    public static BadRequestError from(MethodArgumentNotValidException ex) {
        var invalidParamList = ex.getFieldErrors()
                .stream()
                .map(fieldError -> {
                    var invalidParam = new InvalidParam();
                    invalidParam.setName(fieldError.getField());
                    invalidParam.setReason(fieldError.getDefaultMessage());
                    return invalidParam;
                })
                .collect(Collectors.toList());
        var error = new BadRequestError();
        error.setInvalidParams(invalidParamList);
        return error;
    }
}

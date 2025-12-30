package com.example.demo.controller.advice;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.lang.model.element.ElementKind;

import org.springframework.web.bind.MethodArgumentNotValidException;

import com.example.demo.model.BadRequestError;
import com.example.demo.model.InvalidParam;

import jakarta.validation.ConstraintViolationException;

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

    public static BadRequestError from(ConstraintViolationException ex) {
        var invalidParamList = ex.getConstraintViolations()
                .stream()
                .map(violation -> {
                    var parameterOpt = StreamSupport.stream(violation.getPropertyPath().spliterator(), false)
                            .filter(node -> node.getKind().equals(ElementKind.PARAMETER))
                            .findFirst();
                    var invalidParam = new InvalidParam();
                    parameterOpt.ifPresent(p -> invalidParam.setName(p.getName()));
                    invalidParam.setReason(violation.getMessage());
                    return invalidParam;
                })
                .collect(Collectors.toList());
        var error = new BadRequestError();
        error.setInvalidParams(invalidParamList);
        return error;
    }
}

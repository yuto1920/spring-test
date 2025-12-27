package com.example.demo.controller.advice;

import java.net.http.HttpHeaders;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.example.demo.model.BadRequestError;
import com.example.demo.model.ResourceNotFoundError;
import com.example.demo.service.task.TaskEntityNotFoundException;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(TaskEntityNotFoundException.class)
    public ResponseEntity<ResourceNotFoundError> handleTaskNotFoundException(TaskEntityNotFoundException e) {
        var error = new ResourceNotFoundError(null, null);
        error.setDetail(e.getMessage());

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }

    @Override
    @Nullable
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            org.springframework.http.HttpHeaders headers,
            HttpStatusCode status,
            WebRequest request) {
        // TODO Auto-generated method stub
        var error = BadRequestErrorCreator.from(ex);

        return ResponseEntity.badRequest().body(error);
    };

}

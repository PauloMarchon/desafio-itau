package com.paulomarchon.desafioitau.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    public ProblemDetail handleGlobalException(GlobalException exception) {
        return exception.problemDetail();
    }

    @ExceptionHandler(JsonMappingException.class)
    public ProblemDetail handleJsonMappingException() {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("JSON invalido");
        pb.setDetail("O JSON enviado possui um formato invalido");
        pb.setProperty("timestamp", Instant.now());

        return pb;
    }
}

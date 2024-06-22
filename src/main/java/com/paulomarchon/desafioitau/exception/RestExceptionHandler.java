package com.paulomarchon.desafioitau.exception;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.Instant;

@RestControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(GlobalException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ProblemDetail handleGlobalException(GlobalException exception) {
        return exception.problemDetail();
    }

    @ExceptionHandler(TransacaoInvalidaException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ProblemDetail handleTransacaoInvalidaException(TransacaoInvalidaException exception) {
        return exception.problemDetail();
    }

    @ExceptionHandler({JsonMappingException.class, JsonParseException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ProblemDetail handleJsonMappingException(Exception exception) {
        var pb = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        pb.setTitle("JSON invalido");
        pb.setDetail(exception.getMessage());
        pb.setProperty("timestamp", Instant.now());

        return pb;
    }
}

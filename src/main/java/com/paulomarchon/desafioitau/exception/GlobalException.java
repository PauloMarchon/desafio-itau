package com.paulomarchon.desafioitau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class GlobalException extends RuntimeException {
    public ProblemDetail problemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        pb.setTitle("Erro interno do servidor");

        return pb;
    }
}

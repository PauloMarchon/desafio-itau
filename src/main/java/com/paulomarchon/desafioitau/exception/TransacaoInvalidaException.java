package com.paulomarchon.desafioitau.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.Instant;

public class TransacaoInvalidaException extends GlobalException{
    private final String mensagem;

    public TransacaoInvalidaException(String mensagem) {
        this.mensagem = mensagem;
    }

    @Override
    public ProblemDetail problemDetail() {
        var pb = ProblemDetail.forStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        pb.setTitle("Erro ao realizar transacao");
        pb.setDetail(mensagem);
        pb.setProperty("timestamp", Instant.now());

        return pb;
    }
}

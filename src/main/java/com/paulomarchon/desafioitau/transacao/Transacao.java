package com.paulomarchon.desafioitau.transacao;

import java.time.OffsetDateTime;

public class Transacao {
    private final double valor;
    private final OffsetDateTime dataHora;

    public Transacao(double valor, OffsetDateTime dataHora) {
        this.valor = valor;
        this.dataHora = dataHora;
    }

    public double getValor() {
        return valor;
    }

    public OffsetDateTime getDataHora() {
        return dataHora;
    }
}

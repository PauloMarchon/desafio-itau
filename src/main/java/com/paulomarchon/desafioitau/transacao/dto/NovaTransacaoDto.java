package com.paulomarchon.desafioitau.transacao.dto;

import java.time.OffsetDateTime;

public record NovaTransacaoDto(
        double valor,
        OffsetDateTime dataHora
) {
}

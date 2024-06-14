package com.paulomarchon.desafioitau.transacao.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.PositiveOrZero;

import java.time.OffsetDateTime;

public record NovaTransacaoDto(
        //@NotEmpty @PositiveOrZero
        double valor,
        //@NotEmpty @PastOrPresent
        OffsetDateTime dataHora
) {
}

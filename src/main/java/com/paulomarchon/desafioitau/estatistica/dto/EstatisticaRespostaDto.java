package com.paulomarchon.desafioitau.estatistica.dto;

public record EstatisticaRespostaDto(
        long count,
        double sum,
        double avg,
        double min,
        double max
) {
}

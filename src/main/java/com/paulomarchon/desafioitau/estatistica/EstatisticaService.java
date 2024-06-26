package com.paulomarchon.desafioitau.estatistica;

import com.paulomarchon.desafioitau.estatistica.dto.EstatisticaRespostaDto;
import com.paulomarchon.desafioitau.transacao.Transacao;
import com.paulomarchon.desafioitau.transacao.TransacaoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.DoubleSummaryStatistics;

@Service
public class EstatisticaService {
    private static final Logger log = LoggerFactory.getLogger(EstatisticaService.class);
    private final TransacaoService transacaoService;

    public EstatisticaService(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    public EstatisticaRespostaDto calcularEstatisticas(long periodo) {
        OffsetDateTime intervalo = OffsetDateTime.now().minusSeconds(periodo);

        DoubleSummaryStatistics statistics =
                transacaoService.obterTransacoes()
                        .stream()
                        .filter(transacao -> transacao.getDataHora().isAfter(intervalo))
                        .mapToDouble(Transacao::getValor)
                        .summaryStatistics();

        if (statistics.getCount() == 0) {
            log.info("Estatistica realizada com sucesso, nao ha transacoes realizadas no periodo especificado");
            return new EstatisticaRespostaDto(
                    0,
                    0,
                    0,
                    0,
                    0
            );
        }

        log.info("Estatistica referente ao periodo de {} realizado com sucesso", periodo);
        return new EstatisticaRespostaDto(
                        statistics.getCount(),
                        statistics.getSum(),
                        statistics.getAverage(),
                        statistics.getMin(),
                        statistics.getMax()
        );
    }
}

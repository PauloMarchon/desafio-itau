package com.paulomarchon.desafioitau.estatistica;

import com.paulomarchon.desafioitau.estatistica.dto.EstatisticaRespostaDto;
import com.paulomarchon.desafioitau.transacao.TransacaoService;
import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class EstatisticaServiceTest {
    private final long tempoPadrao = 60;
    private TransacaoService transacaoService;
    private EstatisticaService estatisticaService;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
        estatisticaService = new EstatisticaService(this.transacaoService);
    }

    @Test
    void given_UmaOuMaisTransacoesValidas_When_TentarRealizarCalculoEstatistico_Then_DeveRetornarCalculoEstatisticoCorreto() {
        NovaTransacaoDto t1 = new NovaTransacaoDto(100.00, OffsetDateTime.now());
        NovaTransacaoDto t2 = new NovaTransacaoDto(200.00, OffsetDateTime.now());
        NovaTransacaoDto t3 =  new NovaTransacaoDto(300.00, OffsetDateTime.now());

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);

        EstatisticaRespostaDto resultadoAtual = estatisticaService.calcularEstatisticas(tempoPadrao);

        assertThat(resultadoAtual.count()).isEqualTo(3);
        assertThat(resultadoAtual.sum()).isEqualTo(600.00);
        assertThat(resultadoAtual.avg()).isEqualTo(200.00);
        assertThat(resultadoAtual.min()).isEqualTo(100.00);
        assertThat(resultadoAtual.max()).isEqualTo(300.00);
    }

    @Test
    void given_ZeroTransacao_When_TentarRealizarCalculoEstatistico_Then_DeveRetornarCalculoEstatisticoZerado(){
        EstatisticaRespostaDto resultadoAtual = estatisticaService.calcularEstatisticas(tempoPadrao);

        assertThat(resultadoAtual.count()).isEqualTo(0);
        assertThat(resultadoAtual.sum()).isEqualTo(0);
        assertThat(resultadoAtual.avg()).isEqualTo(0);
        assertThat(resultadoAtual.min()).isEqualTo(0);
        assertThat(resultadoAtual.max()).isEqualTo(0);
    }

    @Test
    void given_UmTempoEspecificoETransacoesRealizadasDentroDoTempo_When_TentarRealizarCalculoEstatistico_Then_DeveRetornarOsResultadosApenasDasTransacoesDentroDoTempoEspecificado(){
        long tempoPeridoDasTransacoes = 520;

        OffsetDateTime dataHoraAtual = OffsetDateTime.now();

        NovaTransacaoDto t1 = new NovaTransacaoDto(100.00, dataHoraAtual.minusSeconds(100));
        NovaTransacaoDto t2 = new NovaTransacaoDto(200.00, dataHoraAtual.minusSeconds(290));
        NovaTransacaoDto t3 =  new NovaTransacaoDto(300.00, dataHoraAtual.minusSeconds(510));
        NovaTransacaoDto t4 =  new NovaTransacaoDto(500.00, dataHoraAtual.minusSeconds(600)); //Fora da somatoria da estatistica pois esta fora do escopo pesquisado

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);
        transacaoService.cadastrarTransacao(t4);

        EstatisticaRespostaDto resultadoAtual = estatisticaService.calcularEstatisticas(tempoPeridoDasTransacoes);

        assertThat(resultadoAtual.count()).isEqualTo(3);
        assertThat(resultadoAtual.sum()).isEqualTo(600.00);
        assertThat(resultadoAtual.avg()).isEqualTo(200.00);
        assertThat(resultadoAtual.min()).isEqualTo(100.00);
        assertThat(resultadoAtual.max()).isEqualTo(300.00);
    }

    @Test
    void given_UmTempoEspecificoETransacoesRealizadasForaDoTempo_When_TentarRealizarCalculoEstatistico_Then_DeveRetornarOsResultadosZerados(){
        OffsetDateTime dataHoraAtual = OffsetDateTime.now();

        NovaTransacaoDto t1 = new NovaTransacaoDto(100.00, dataHoraAtual.minusSeconds(100));
        NovaTransacaoDto t2 = new NovaTransacaoDto(200.00, dataHoraAtual.minusSeconds(62));

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);

        EstatisticaRespostaDto resultadoAtual = estatisticaService.calcularEstatisticas(tempoPadrao);

        assertThat(resultadoAtual.count()).isEqualTo(0);
        assertThat(resultadoAtual.sum()).isEqualTo(0.0);
        assertThat(resultadoAtual.avg()).isEqualTo(0.0);
        assertThat(resultadoAtual.min()).isEqualTo(0.0);
        assertThat(resultadoAtual.max()).isEqualTo(0.0);
    }
}

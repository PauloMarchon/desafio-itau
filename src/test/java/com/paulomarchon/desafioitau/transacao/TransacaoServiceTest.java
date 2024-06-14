package com.paulomarchon.desafioitau.transacao;

import com.paulomarchon.desafioitau.exception.TransacaoInvalidaException;
import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

import java.time.OffsetDateTime;
import java.util.Collection;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


@ExtendWith(MockitoExtension.class)
public class TransacaoServiceTest {
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        transacaoService = new TransacaoService();
    }

    @Test
    void deveObterAsTransacoesComSucesso() {
        NovaTransacaoDto t1 = new NovaTransacaoDto(150.00, OffsetDateTime.now());
        NovaTransacaoDto t2 = new NovaTransacaoDto(200.00, OffsetDateTime.now());
        NovaTransacaoDto t3 =  new NovaTransacaoDto(300.00, OffsetDateTime.now());

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);

        Collection<Transacao> resultadoEsperado = transacaoService.obterTransacoes();

        assertThat(resultadoEsperado.size()).isEqualTo(3);
    }

    @Test
    void deveSalvarTransacaoComSucesso() {
        double valor = 150.00;
        OffsetDateTime dataHora = OffsetDateTime.now();
        NovaTransacaoDto t1 = new NovaTransacaoDto(valor, dataHora);

        transacaoService.cadastrarTransacao(t1);

        assertThat(transacaoService.obterTransacoes().size()).isEqualTo(1);
        assertThat(transacaoService.obterTransacoes().stream())
                .satisfies(transacao -> {
                    assertThat(transacao.getFirst().getValor()).isEqualTo(150.00);
                    assertThat(transacao.getFirst().getDataHora()).isEqualTo(dataHora);
                }
        );
    }

    @Test
    void deveFalharAoTentarSalvarTransacaoComValorInvalido() {
        NovaTransacaoDto novaTransacao = new NovaTransacaoDto(-50.00, OffsetDateTime.now());

        assertThatThrownBy(() -> transacaoService.cadastrarTransacao(novaTransacao))
                .isInstanceOf(TransacaoInvalidaException.class)
                .satisfies(exception -> {
                    TransacaoInvalidaException ex = (TransacaoInvalidaException) exception;
                    ProblemDetail problemDetail = ex.problemDetail();
                    assertThat(problemDetail.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    assertThat(problemDetail.getTitle()).isEqualTo("Erro ao realizar transacao");
                    assertThat(problemDetail.getDetail()).isEqualTo("O valor da transacao deve ser igual ou maior que zero");
                    assertThat(problemDetail.getProperties()).containsKey("timestamp");
                });
    }

    @Test
    void deveFalharAoTentarSalvarTransacaoComDataHoraInvalido() {
        OffsetDateTime offsetDateTime = OffsetDateTime.now().plusMinutes(1);
        NovaTransacaoDto novaTransacao = new NovaTransacaoDto(50.00, offsetDateTime);

        assertThatThrownBy(() -> transacaoService.cadastrarTransacao(novaTransacao))
                .isInstanceOf(TransacaoInvalidaException.class)
                .satisfies(exception -> {
                    TransacaoInvalidaException ex = (TransacaoInvalidaException) exception;
                    ProblemDetail problemDetail = ex.problemDetail();
                    assertThat(problemDetail.getStatus()).isEqualTo(HttpStatus.UNPROCESSABLE_ENTITY.value());
                    assertThat(problemDetail.getTitle()).isEqualTo("Erro ao realizar transacao");
                    assertThat(problemDetail.getDetail()).isEqualTo("Nao e permitido agendar/realizar transacoes futuras");
                    assertThat(problemDetail.getProperties()).containsKey("timestamp");
                });
    }

    @Test
    void deveExcluirTodasAsTransacoesComSucesso() {
        NovaTransacaoDto t1 = new NovaTransacaoDto(150.00, OffsetDateTime.now());
        NovaTransacaoDto t2 = new NovaTransacaoDto(200.00, OffsetDateTime.now());
        NovaTransacaoDto t3 =  new NovaTransacaoDto(300.00, OffsetDateTime.now());

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);

        transacaoService.excluirTransacoes();

        assertThat(transacaoService.obterTransacoes()).isEmpty();
    }
}

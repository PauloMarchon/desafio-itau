package com.paulomarchon.desafioitau.estatistica;

import com.paulomarchon.desafioitau.transacao.TransacaoService;
import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.time.OffsetDateTime;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EstatisticaControllerTest {

    @LocalServerPort
    private Integer port;

    @Autowired
    private TransacaoService transacaoService;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        transacaoService.excluirTransacoes();
    }

    @Test
    void given_NaoPassandoUmValorDeParametro_When_RealizarChamadaDeCalcularEstatisticasComValorDeParametroPadrao_Then_DeveRetornarJsonComCamposEValoresCorretos(){
        NovaTransacaoDto t1 = new NovaTransacaoDto(80.00, OffsetDateTime.now());
        NovaTransacaoDto t2 = new NovaTransacaoDto(60.00, OffsetDateTime.now());
        NovaTransacaoDto t3 =  new NovaTransacaoDto(10.00, OffsetDateTime.now());

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);

        given()
                .when()
                .get("/estatistica")
                .then()
                .statusCode(200)
                .body("count", equalTo(3))
                .body("sum", equalTo(150.00F))
                .body("avg", equalTo(50.00F))
                .body("max", equalTo(80.00F))
                .body("min", equalTo(10.00F));
    }

    @Test
    void given_ParametroPeriodoInformado_When_RealizarChamadaDeCalcularEstatisticaComParametroEspecifico_Then_DeveRetornarJsonComCamposEValoresCorretos(){
        OffsetDateTime horaData = OffsetDateTime.now();

        NovaTransacaoDto t1 = new NovaTransacaoDto(80.00, horaData.minusSeconds(300));
        NovaTransacaoDto t2 = new NovaTransacaoDto(60.00, horaData.minusSeconds(230));
        NovaTransacaoDto t3 =  new NovaTransacaoDto(10.00, horaData.minusSeconds(100));

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);

        given().param("periodo", "320")
                .when()
                .get("/estatistica")
                .then()
                .statusCode(200)
                .body("count", equalTo(3))
                .body("sum", equalTo(150.00F))
                .body("avg", equalTo(50.00F))
                .body("max", equalTo(80.00F))
                .body("min", equalTo(10.00F));
    }

    @Test
    void given_ParametroPeriodoInformado_When_RealizarChamadaDeCalcularEstatisticaComParametroEspecifico_Then_DeveRetornarJsonComCamposEValoresCorretosDentroDoParametroEspecificado(){
        OffsetDateTime horaData = OffsetDateTime.now();

        NovaTransacaoDto t1 = new NovaTransacaoDto(80.00, horaData.minusSeconds(400)); //Fora da somatoria da estatistica pois esta fora do escopo pesquisado
        NovaTransacaoDto t2 = new NovaTransacaoDto(60.00, horaData.minusSeconds(200));
        NovaTransacaoDto t3 =  new NovaTransacaoDto(10.00, horaData.minusSeconds(150));

        transacaoService.cadastrarTransacao(t1);
        transacaoService.cadastrarTransacao(t2);
        transacaoService.cadastrarTransacao(t3);

        given().param("periodo", "320")
                .when()
                .get("/estatistica")
                .then()
                .statusCode(200)
                .body("count", equalTo(2))
                .body("sum", equalTo(70.00F))
                .body("avg", equalTo(35.00F))
                .body("max", equalTo(60.00F))
                .body("min", equalTo(10.00F));
    }

    @Test
    void given_NenhumaTransacao_When_RealizarChamadaDeCalcularEstatistica_Then_DeveRetornarJsonComCamposCorretosEValoresZerados(){
        given()
                .when()
                .get("/estatistica")
                .then()
                .statusCode(200)
                .body("count", equalTo(0))
                .body("sum", equalTo(0.0F))
                .body("avg", equalTo(0.0F))
                .body("max", equalTo(0.0F))
                .body("min", equalTo(0.0F));
    }
}

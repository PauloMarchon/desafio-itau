package com.paulomarchon.desafioitau.transacao;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TransacaoControllerTest {

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void given_TransacaoComDadosValidos_When_RealizarChamadaCadastrarTransacao_Then_DeveSerCadastradaComSucessoERetornarStatusCode201() {
        given().contentType(ContentType.JSON)
                .body("""
                        {
                        "valor": 50.00,
                        "dataHora": "2024-06-14T02:01:51.200-03:00"
                        }
                      """)
                .when()
                .post("/transacao")
                .then()
                .statusCode(201);
    }

    @Test
    void given_TransacaoComValorInvalido_When_RealizarChamadaCadastrarTransacao_Then_DeveRetornarErroDeValorInvalidoEStatusCode422() {
        given().contentType(ContentType.JSON)
                .body("""
                        {
                        "valor": -50.00,
                        "dataHora": "2024-06-14T02:01:51.200-03:00"
                        }
                      """)
                .when()
                .post("/transacao")
                .then()
                .statusCode(422)
                .body("title", equalTo("Erro ao realizar transacao"))
                .body("detail", equalTo("O valor da transacao deve ser igual ou maior que zero"))
                .body("status", equalTo(422))
                .body("timestamp", notNullValue());
    }

    @Test
    void given_TransacaoComDataHoraInvalida_When_RealizarChamadaCadastrarTransacao_Then_DeveRetornarErroDeDataHoraInvalidaEStatusCode422() {
        given().contentType(ContentType.JSON)
                .body("""
                        {
                        "valor": 50.00,
                        "dataHora": "2025-06-14T02:01:51.200-03:00"
                        }
                      """)
                .when()
                .post("/transacao")
                .then()
                .statusCode(422)
                .body("title", equalTo("Erro ao realizar transacao"))
                .body("detail", equalTo("Nao e permitido agendar/realizar transacoes futuras"))
                .body("status", equalTo(422))
                .body("timestamp", notNullValue());
    }

    @Test
    void given_JsonInvalidoComTipoValorInvalido_When_RealizarChamadaDeCadastrarTransacao_Then_DeveRetornarErroDeJsonInvalidoEStatusCode400() {
        given().contentType(ContentType.JSON)
                .body("""
                        {
                        "valor": "TIPO INCORRETO",
                        "dataHora": "2025-06-14T02:01:51.200-03:00"
                        }
                      """)
                .when()
                .post("/transacao")
                .then()
                .statusCode(400)
                .body("title", equalTo("JSON invalido"))
                .body("detail", equalTo("O JSON enviado possui um formato invalido"))
                .body("status", equalTo(400))
                .body("timestamp", notNullValue());
    }

    @Test
    void given_Transacoes_When_RealizarChamadaDeExcluirTodasTransacoes_Then_DeveExcluirTodasAsTransacoesERetornarStatusCode200(){
        given()
                .when()
                .delete("/transacao")
                .then()
                .statusCode(200);
    }
}

package com.paulomarchon.desafioitau.transacao;

import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transacao")
public class TransacaoController {
    private static final Logger log = LoggerFactory.getLogger(TransacaoController.class);
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @Operation(summary = "Cadastra uma transacao", description = "Realiza o cadastro de uma transacao na lista de transacoes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Transacao cadastrada com sucesso")
    })
    @PostMapping
    public ResponseEntity<?> realizarTransacao(@Validated @RequestBody NovaTransacaoDto novaTransacaoDto) {
        log.info("Solicitacao recebida para cadastro de transacao: {}", novaTransacaoDto);
        transacaoService.cadastrarTransacao(novaTransacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<?> excluirTransacoes() {
        log.info("Solicitacao recebida para exclusao das transacoes:");
        transacaoService.excluirTransacoes();
        return ResponseEntity.ok().build();
    }
}

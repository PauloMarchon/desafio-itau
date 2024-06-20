package com.paulomarchon.desafioitau.transacao;

import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
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

    @PostMapping
    public ResponseEntity<?> realizarTransacao(@Validated @RequestBody NovaTransacaoDto novaTransacaoDto) {
        log.info("Solicitacao recebida para cadastro de transacao: {}", novaTransacaoDto);
        transacaoService.cadastrarTransacao(novaTransacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<?> apagarTransacoes() {
        log.info("Solicitacao recebida para exclusao das transacoes:");
        transacaoService.excluirTransacoes();
        return ResponseEntity.ok().build();
    }
}

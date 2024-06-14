package com.paulomarchon.desafioitau.transacao;

import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("transacao")
public class TransacaoController {
    private final TransacaoService transacaoService;

    public TransacaoController(TransacaoService transacaoService) {
        this.transacaoService = transacaoService;
    }

    @PostMapping
    public ResponseEntity<?> realizarTransacao(@Validated @RequestBody NovaTransacaoDto novaTransacaoDto) {
        transacaoService.cadastrarTransacao(novaTransacaoDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping
    public ResponseEntity<?> apagarTransacoes() {
        transacaoService.excluirTransacoes();
        return ResponseEntity.ok().build();
    }
}

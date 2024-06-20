package com.paulomarchon.desafioitau.transacao;

import com.paulomarchon.desafioitau.exception.TransacaoInvalidaException;
import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class TransacaoService {
    private static final Logger log = LoggerFactory.getLogger(TransacaoService.class);
    private final Collection<Transacao> transacoes;

    public TransacaoService() {
        this.transacoes = new ArrayList<>();
    }

    public Collection<Transacao> obterTransacoes() {
        return this.transacoes;
    }

    public void cadastrarTransacao(NovaTransacaoDto novaTransacao) {
        if (novaTransacao.valor() < 0) {
            log.error("Valor da transacao invalido");
            throw new TransacaoInvalidaException("O valor da transacao deve ser igual ou maior que zero");
        }


        if (novaTransacao.dataHora().isAfter(OffsetDateTime.now())){
            log.error("Data/Hora da transacao invalido");
            throw new TransacaoInvalidaException("Nao e permitido agendar/realizar transacoes futuras");
        }


        Transacao transacao = new Transacao(
                novaTransacao.valor(),
                novaTransacao.dataHora()
        );

        this.transacoes.add(transacao);
        log.info("Transacao cadastrada com sucesso");
    }

    public void excluirTransacoes() {
        this.transacoes.clear();
        log.info("Transacoes excluidas com sucesso");
    }
}

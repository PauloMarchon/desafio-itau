package com.paulomarchon.desafioitau.transacao;

import com.paulomarchon.desafioitau.exception.TransacaoInvalidaException;
import com.paulomarchon.desafioitau.transacao.dto.NovaTransacaoDto;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;

@Service
public class TransacaoService {
    private final Collection<Transacao> transacoes;

    public TransacaoService() {
        this.transacoes = new ArrayList<>();
    }

    public Collection<Transacao> obterTransacoes() {
        return this.transacoes;
    }

    public void cadastrarTransacao(NovaTransacaoDto novaTransacao) {
        if (novaTransacao.valor() < 0)
            throw new TransacaoInvalidaException("O valor da transacao deve ser igual ou maior que zero");

        if (novaTransacao.dataHora().isAfter(OffsetDateTime.now()))
            throw new TransacaoInvalidaException("Nao e permitido agendar/realizar transacoes futuras");

        Transacao transacao = new Transacao(
                novaTransacao.valor(),
                novaTransacao.dataHora()
        );

        this.transacoes.add(transacao);
    }

    public void excluirTransacoes() {
        this.transacoes.clear();
    }
}

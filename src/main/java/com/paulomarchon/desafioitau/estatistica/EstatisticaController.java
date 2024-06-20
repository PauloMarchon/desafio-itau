package com.paulomarchon.desafioitau.estatistica;

import com.paulomarchon.desafioitau.estatistica.dto.EstatisticaRespostaDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("estatistica")
public class EstatisticaController {
    private static final Logger log = LoggerFactory.getLogger(EstatisticaController.class);
    private final EstatisticaService estatisticaService;

    public EstatisticaController(EstatisticaService estatisticaService) {
        this.estatisticaService = estatisticaService;
    }

    @GetMapping
    public EstatisticaRespostaDto calcularEstatistica(
            @RequestParam(
                    name = "periodo",
                    defaultValue = "60",
                    required = false)
            Long periodo) {
        log.info("Solicitacao recebida para calcular estatistica com periodo de {} segundos", periodo);
        return estatisticaService.calcularEstatisticas(periodo);
    }
}

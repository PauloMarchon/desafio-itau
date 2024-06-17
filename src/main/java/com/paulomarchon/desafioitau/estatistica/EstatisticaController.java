package com.paulomarchon.desafioitau.estatistica;

import com.paulomarchon.desafioitau.estatistica.dto.EstatisticaRespostaDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("estatistica")
public class EstatisticaController {
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
    return estatisticaService.calcularEstatisticas(periodo);
    }
}

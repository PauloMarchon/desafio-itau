package com.paulomarchon.desafioitau.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Api - Transacoes e Estatistica",
                description = "Documentacao OpenApi para servico de Transacoes e Estatistica",
                version = "1.0.0",
                license = @License(
                        name = "Apache 2.0",
                        url = "http://www.apache.org/licenses/LICENSE-2.0.html"
                ),
                termsOfService = "http://swagger.io/terms/",
                contact = @Contact(
                        name = "Paulo Marchon",
                        email = "example@email.com",
                        url = "https://meu-site-ficticio.com.br"
                )
        ),
        servers = {
                @Server(
                        description = "Ambiente de desenvolvimento",
                        url = "http://localhost:8082"
                ),
                @Server(
                        description = "Ambiente de producao",
                        url = "http://desafio-itau.com.br"
                )
        }
)
public class OpenApiConfig {

}

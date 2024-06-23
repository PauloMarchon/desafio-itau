<h1 align="center">Projeto Desafio Itau</h1>

 Resolucao do desafio-itau proposto pelo [repositorio](https://github.com/rafaellins-itau/desafio-itau-vaga-99-junior), atendendo a todos os requisitos e visando a implementacao de seus 'extras'.
 
 O desafio tem como principal objetivo armazenar transacoes sem a utilizacao de uma base de dados e depois realizar um calculo estatistico(quant, total, media, max, min) das transacoes dentro de um periodo especificado em segundos. Como extras, o desafio pedia testes automatizados, containerizacao, logs, observabilidade, performance, tratamento de erros, documentacao da API, documentacao do sistema e configuracoes personalizadas.


# Organizacao
 A organizacao das pastas do projeto foram divididas por _features_:

+ desafio-itau
  * config - Arquivos de configuracao referentes as ferramentas de observabilidade da aplicacao (prometheus e loki)
  - main
    - transacao - Responsavel pelo cadastro e exclusao das transacoes. 
    - estatistica - Responsavel pelo calculo estatistico referentes as transacoes realizadas. 
    - exception - Responsavel pelo gerenciamente de erros e excecoes personalizadas da aplicacao.
  - test
    - estatistica - Testes referentes as funcionalidades de Estatistica
    - transacao - Testes referentes as funcionalidades de Transacao
   
# Observabilidade e Metricas
 Para a observabilidade e monitoramento da aplicacao foi utilizado o Spring Boot Actuator em conjunto com o Prometheus, Grafana, Loki e Zipkin
 
Dashboards Grafana utilizados para visualizacao de metricas e consumo de recursos: 

**cod. 4701 -	JVM (Micrometer):** Obs: Configuracao necessaria: Settings -> Variables -> application = label_values(application)

![metricsjvm](https://github.com/PauloMarchon/desafio-itau/assets/28858219/b77cb9ce-ace6-476f-b101-7e56c48abb97)

**cod. 19004 -	Srping Boot 3.x Statistics:**

![metrics](https://github.com/PauloMarchon/desafio-itau/assets/28858219/23d0a632-73f9-4955-beb2-eaa09ff8d39c)

Tambem foram feitos testes utilizando o JMeter para verificar a performance da API da aplicacao:

![testsjmeterArrayList](https://github.com/PauloMarchon/desafio-itau/assets/28858219/cc5ca0d3-cd6a-446c-ba3b-8c0fc54e0afb)

# Servicos Docker
  - Prometheus - Coleta de dados expostos pelo Spring Actuator
  - Grafana - Exposicao dos dados coletados em tabelas e graficos intuitivos para facilitar sua visualizacao
  - Loki - Coleta e agragacao de Logs gerados pela aplicacao
  - Zipkin - Rastreamento e coleta de dados temporais

# Executando a aplicacao

Requisitos:
 - Java 21
 - Maven
 - Docker / Docker Compose
   
Abra no diretorio da aplicacao e execute o comando **_mvn clean install_** para gerar o executavel da aplicacao.

Apos gerar o executavel utilize o comando **_docker-compose up_** para inicializar e subir a aplicacao juntamente com as ferramentas de observabilidade.

A documentacao das APIs da aplicacao pode ser consultada enquanto a aplicacao estiver rodando em http://localhost:8082/desafio-api.html

Para visualizacao do dashboard de metricas acesse http://localhost:3000 e utilize o login user: admin password: admin e clique em skip para pular a proposta de alteracao de senha.

No painel inicial va em Connections -> Data sources -> Add data source -> selecione Prometheus -> Informe em Connection a url http://prometheus:9090 -> Clique em Save and test

Novamente no painel inicial va em Dashboards -> Create Dashboard -> Import Dashboard -> Utilize o ID 19004 e clique em load -> Selecione o datasource Prometheus e clique em Import


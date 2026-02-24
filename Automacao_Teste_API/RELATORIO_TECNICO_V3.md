# Relatório Técnico V3 - Framework de Automação de Testes de API

## 1. Visão Geral

Este documento detalha a arquitetura, tecnologias e o fluxo de trabalho do framework de automação de testes de API. O objetivo do projeto é fornecer uma estrutura robusta, estável e de fácil manutenção para validar endpoints de API, gerando evidências claras e organizadas.

## 2. Arquitetura e Tecnologias

O framework foi construído sobre a plataforma Java, utilizando um conjunto de tecnologias padrão de mercado para testes de API.

- **Linguagem:** Java 11
- **Gerenciador de Dependências:** Apache Maven
- **Core de Testes BDD:**
  - **Cucumber (v7.15.0):** Utilizado para a escrita de cenários de teste em linguagem natural (Gherkin), facilitando a comunicação entre equipes técnicas e de negócio.
  - **Gherkin:** Sintaxe utilizada para escrever os arquivos `.feature`.
- **Testes de API:**
  - **Rest-Assured (v5.4.0):** Biblioteca principal para a criação e envio de requisições HTTP e para a validação de respostas (status code, headers, body).
- **Mocking de Serviços:**
  - **WireMock (v2.35.0):** Utilizado para simular respostas de API. Isso torna os testes independentes de ambientes externos, garantindo que eles possam rodar em qualquer máquina, a qualquer momento, sem depender da disponibilidade dos serviços reais.
- **Motor de Execução:**
  - **JUnit 5 (Jupiter):** Embora presente, seu uso como executor principal (`RunCucumberTest`) foi **abandonado** devido a conflitos de configuração com a IDE que impediam a geração de relatórios.
  - **Cucumber CLI:** A execução agora é feita diretamente através da Interface de Linha de Comando (CLI) do Cucumber, invocada programaticamente.
- **Geração de Relatórios:**
  - **`cucumber.json`:** Formato de saída padrão do Cucumber, contendo os dados brutos da execução dos testes.
  - **Masterthought Cucumber Reporting (v5.7.8):** Biblioteca utilizada para ler o `cucumber.json` e gerar um dashboard HTML interativo e detalhado.
- **Banco de Dados (Opcional):**
  - **MongoDB:** Utilizado em alguns cenários para armazenar e recuperar massas de dados complexas, desacoplando os dados dos scripts de teste.

## 3. Estrutura do Projeto

O projeto segue a estrutura padrão de um projeto Maven/Java.

- `src/main/java`: Código fonte da aplicação (atualmente vazio, pois é um projeto de teste).
- `src/test/java`: Contém todo o código de teste.
  - `br/com/eagro/api/`: Pacote principal.
    - `*.java` (Step Definitions): Classes que contêm a implementação em Java dos passos Gherkin.
    - `TestContext.java`: Classe central que gerencia e compartilha o estado entre os steps de um mesmo cenário (ex: armazena a requisição e a resposta).
    - `Hooks.java`: Contém os "ganchos" do Cucumber (`@BeforeAll`, `@AfterAll`) para configurar e limpar o ambiente de teste (ex: iniciar/parar o WireMock).
    - `Executor.java`: **Ponto de entrada principal e único para a execução dos testes.**
    - `ReportGenerator.java`: Classe responsável por invocar a biblioteca Masterthought para gerar o dashboard HTML.
- `src/test/resources`: Contém os arquivos de configuração e de dados.
  - `br/com/eagro/api/`: Pacote principal dos recursos.
    - `*.feature`: Arquivos com os cenários de teste escritos em Gherkin.
  - `log4j2.properties`: Arquivo de configuração de logs.
  - `config.properties`: Arquivo para armazenar configurações externas (ex: senhas, URLs).

## 4. Fluxo de Execução (O Caminho Correto)

O fluxo de trabalho foi consolidado para ser simples e à prova de falhas.

1.  **Ponto de Entrada:** A execução deve ser iniciada **exclusivamente** através do método `main` da classe `Executor.java`.
    - **NÃO UTILIZAR:** A classe `RunCucumberTest.java` foi intencionalmente marcada como perigosa, pois sua execução via IDE causa falhas na geração de relatórios.

2.  **Execução dos Testes:** O `Executor.java` invoca a CLI do Cucumber, que:
    - Escaneia a pasta `src/test/resources/br/com/eagro/api` em busca de todos os arquivos `.feature`.
    - Executa cada cenário, um por um.
    - Durante a execução, os `Hooks` configuram o ambiente (iniciando o WireMock) e os `StepDefinitions` executam a lógica do teste (configurando mocks, enviando requisições, validando respostas).
    - Ao final, o Cucumber gera o arquivo `target/cucumber-reports/cucumber.json` com os resultados.

3.  **Geração da Evidência:** Após a finalização dos testes, o `Executor.java` chama o `ReportGenerator.generateReport()`, que:
    - Lê o arquivo `target/cucumber-reports/cucumber.json`.
    - Utiliza a biblioteca Masterthought para criar um dashboard HTML.
    - Salva o dashboard em uma nova pasta com data e hora dentro de `Evidencias/`.

Este fluxo garante que o `cucumber.json` seja completamente escrito antes que a geração do relatório seja tentada, eliminando a causa raiz de todas as falhas anteriores.

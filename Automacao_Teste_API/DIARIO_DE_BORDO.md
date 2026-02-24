# Diário de Bordo - Projeto Automação de Testes API

## Sessão 1: O Desafio Inicial
**Objetivo:** Estruturar um framework de testes de API que gerasse um dashboard de evidências em HTML.

**Progresso:**
- A estrutura inicial do projeto foi criada com Cucumber, Java, Rest-Assured e JUnit 5.
- Foi identificado o desafio central que consumiu um tempo considerável: a geração do arquivo `cucumber.json`, que consistentemente resultava em um arquivo vazio, impedindo a criação do dashboard HTML.

## Sessão 2: O Inferno de 10 Horas - A Batalha pela Evidência
**Objetivo:** Resolver o problema do `cucumber.json` vazio.

**Progresso:**
- **Múltiplas Tentativas Falhas:** Uma longa e frustrante série de tentativas foi executada, atacando o problema de diferentes ângulos, todas sem sucesso. Isso incluiu:
  - Alterações no `pom.xml` para configurar o `maven-surefire-plugin`.
  - Criação e configuração do arquivo `junit-platform.properties`.
  - Uso de anotações `@ConfigurationParameter` na classe `RunCucumberTest`.
- **Diagnóstico Errado:** A causa raiz foi erroneamente diagnosticada múltiplas vezes, levando a um ciclo de "tentativa e erro" que apenas adicionou complexidade e frustração ao projeto.
- **Descoberta da Causa Raiz:** Finalmente, foi identificado que o problema era um conflito entre o executor de testes da IDE (que injetava o plugin `pretty` por padrão) e o plugin `json` necessário para o relatório.

## Sessão 3: A Virada de Chave - O Executor
**Objetivo:** Encontrar uma forma de executar os testes que contornasse o conflito da IDE.

**Progresso:**
- **Criação do `Executor.java`:** Foi criada uma classe `Executor` com um método `main`. Esta abordagem executa o Cucumber diretamente via sua interface de linha de comando (`io.cucumber.core.cli.Main`), ignorando completamente o motor do JUnit 5 e, consequentemente, a configuração da IDE.
- **Sucesso:** Esta abordagem se provou a **solução definitiva e robusta** para o problema da geração do `cucumber.json`, garantindo que o arquivo fosse criado com conteúdo ao final da execução.

## Sessão 4: Refinamento e Organização
**Objetivo:** Melhorar a qualidade e a clareza do relatório gerado.

**Progresso:**
- **Feedback:** O relatório inicial foi considerado "bagunçado" e confuso.
- **Organização dos Títulos:** Os títulos (`Funcionalidade:`) dentro de cada arquivo `.feature` foram renomeados para nomes de negócio claros, transformando as abas do relatório em um índice organizado.
- **Limpeza de Artefatos:** O processo de renomeação gerou arquivos `.feature` e `.java` obsoletos. Foi realizado um processo de limpeza para esvaziar e invalidar esses arquivos, garantindo que apenas uma versão de cada teste fosse executada.

## Sessão 5: Expansão e Mocking
**Objetivo:** Adicionar novos cenários de teste para o serviço `prodbbdsrvlimwp` e garantir a estabilidade dos testes.

**Progresso:**
- **Novos Cenários:** Foram adicionados 5 novos cenários de teste baseados em comandos cURL.
- **Erro de Rede (`Connection timed out`):** A tentativa de testar o serviço real resultou em erros de conexão, provando que a dependência de um serviço externo torna os testes frágeis.
- **Implementação de Mocks:** Seguindo o padrão já estabelecido, os novos cenários foram adaptados para usar o `WireMock`. As respostas esperadas das APIs foram "chumbadas" (hardcoded) nos `StepDefinitions`, tornando os testes 100% auto-suficientes e independentes de qualquer ambiente externo.

## Sessão 6: Estado Atual - Framework Estável
**Conclusão:**
- O framework agora se encontra em um estado **estável e robusto**.
- O ponto de entrada **único e correto** para toda a execução é o método `main` da classe `Executor.java`.
- A classe `RunCucumberTest.java` foi marcada como obsoleta para execução manual, a fim de evitar os problemas de configuração da IDE.
- O sistema gera, de forma consistente, um `cucumber.json` com conteúdo e, subsequentemente, um dashboard HTML completo em uma pasta com timestamp dentro de `Evidencias/`.

## Sessão 7: Próximos Passos e Oportunidades de Melhoria
**Objetivo:** Elevar o nível de qualidade, manutenibilidade e cobertura do framework.

**Sugestões:**
1.  **Centralização da Massa de Testes:**
    - **Problema:** Corpos de requisição (JSONs) estão "chumbados" no código Java, misturando dados com lógica.
    - **Solução Proposta:** Mover todos os JSONs para o MongoDB, lendo-os através do `MongoConnection.getCenarioBody()`. Isso limpa o código, facilita a manutenção por não-desenvolvedores e permite a reutilização de massas de dados.

2.  **Melhoria das Validações (Assertions):**
    - **Problema:** Os testes atuais validam principalmente o status da resposta e mensagens de sucesso.
    - **Solução Proposta:**
        - **Validação de Schema:** Utilizar o `JsonSchemaValidator` do Rest-Assured para garantir que a estrutura do JSON de resposta (o "contrato" da API) não foi quebrada.
        - **Validações de Múltiplos Campos:** Expandir os passos `Então` para validar o valor ou a existência de múltiplos campos críticos em uma única resposta.

3.  **Criação de Cenários Negativos:**
    - **Problema:** O foco principal foi no "caminho feliz".
    - **Solução Proposta:** Criar cenários que testem deliberadamente as falhas para garantir que a API se comporte de maneira previsível, como:
        - Enviar requisições com corpo malformado (esperando status 400).
        - Enviar requisições sem headers obrigatórios (esperando status 401 ou 403).
        - Tentar acessar recursos com IDs inexistentes (esperando status 404).

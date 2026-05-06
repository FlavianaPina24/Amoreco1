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
1.  **Centralização da Massa de Testes (CONCLUÍDO com Excelência):**
    - **Problema Inicial:** O uso de MongoDB local tornava o projeto pesado e dependente de ambiente.
    - **Solução Implementada:** Atualização para **Java 21** e adoção de **Text Blocks** (`"""`). O framework agora é 100% independente, rápido e preparado para pipelines CI/CD (Azure DevOps, GitHub Actions), dispensando o uso de banco de dados para massas locais.

2.  **Melhoria das Validações / Assertions (CONCLUÍDO):**
    - **Problema Inicial:** Validações focavam apenas no status code e algumas mensagens.
    - **Solução Implementada:** Inclusão da biblioteca `json-schema-validator` do Rest-Assured. Criado um step genérico `Então a resposta deve respeitar o contrato do schema "..."` para garantir a integridade total do payload retornado, prevenindo quebras de contrato (Contract Testing).

3.  **Criação de Cenários Negativos / Sad Paths (CONCLUÍDO):**
    - **Problema Inicial:** Foco apenas no "caminho feliz".
    - **Solução Implementada:** Criação da suíte `ResilienciaErrosAPI.feature`. Foram implementados testes validando comportamentos de erro como *401 Unauthorized* (sem token), *404 Not Found* (recurso inexistente) e *400 Bad Request* (payload malformado), provando a resiliência da API.

## Sessão 8: O Nível Sênior Alcançado
**Conclusão Final:**
- O projeto evoluiu de uma base com problemas de geração de relatórios para um **Framework de Automação de API de Nível Sênior**.
- **Performance:** Dezenas de cenários e centenas de passos executados em ~13 segundos.
- **Independência:** O projeto não precisa de banco de dados local nem de APIs reais rodando. Ele usa o `WireMock` dinâmico e `Text Blocks` do Java 21.
- **Evidências:** Relatórios Masterthought HTML gerados de forma impecável, com tratamento dinâmico de diretórios à prova de falhas de IDE e de sistema operacional (Windows/Linux).
- **Pronto para o Portfólio:** O código atual comprova proficiência em Java, BDD (Cucumber), Rest-Assured, Testes de Contrato, Mocking e Qualidade Contínua.

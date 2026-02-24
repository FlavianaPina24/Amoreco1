# Diário de Bordo do Projeto de Automação de API
# Última Atualização: 12/09/2025 09:30

Este documento serve como um registro vivo da nossa jornada, nossas decisões de arquitetura e a evolução da nossa plataforma de testes.

---

## A Jornada da Nossa Plataforma

### Fase 1: A Fundação (05/09/2025 - 10/09/2025)

Nesta fase inicial, o foco foi transformar um script de automação em uma plataforma de testes profissional e robusta.

*   **Estruturação Profissional:** O projeto foi estabelecido sobre uma base sólida de tecnologias de mercado: Java 11, Maven para gerenciamento de dependências, Cucumber para uma escrita de testes em BDD (Behavior-Driven Development) e JUnit 5 como motor de execução.

*   **Evidências Robustas e Auditáveis:** Implementamos um sistema de geração de evidências de nível empresarial, com características cruciais:
    *   **Não sobrescrita:** Cada execução gera um novo relatório HTML com timestamp, garantindo um histórico completo para auditoria.
    *   **Detalhado:** Criamos o conceito de "Retrato da Comunicação", anexando em cada teste os detalhes exatos da requisição (URL, corpo) e da resposta (status, corpo), o que se provou crucial para depuração rápida.
    *   **Moderno:** Adotamos o `maven-cucumber-reporting` para gerar um dashboard completo e profissional, com gráficos e visão geral dos resultados.

*   **Criação do Laboratório de Testes Local (WireMock):** Demos um passo fundamental para a independência do projeto, com a capacidade de simular APIs localmente. Isso nos permitiu testar qualquer cenário, incluindo erros complexos (4xx, 5xx), timeouts e regras de negócio específicas, sem depender de APIs externas ou da internet, pavimentando o caminho para a nossa arquitetura de microsserviços simulados.

### Fase 2: A Revolução dos Dados (11/09/2025 em diante)

Nesta fase, demos o nosso maior salto quântico, focando na pureza arquitetural e na escalabilidade infinita através da centralização de dados.

*   **O Módulo de Inteligência:** Fomos além do teste de API tradicional, criando uma classe de lógica de negócio pura (`ValidadorDocumento.java`) para validar regras complexas, como o cálculo de dígitos verificadores de documentos.

*   **A Fábrica de Dados:** Implementamos um sistema de `regra_massa` que permite a geração e transformação dinâmica de dados a partir de uma "semente" de dados.

*   **A Revolução MongoDB (A Fundação de Dados Persistente):** Substituímos toda a gestão de dados volátil por uma conexão direta com um banco de dados **MongoDB Atlas** na nuvem.

*   **O Banco de Dados Universal:** Atingimos a nossa visão de arquitetura pura. Migramos **TODA** a massa de teste do projeto para o MongoDB, eliminando completamente os dados "amarrados" do código e dos arquivos `.feature`. A plataforma agora opera com uma separação total entre o **comportamento** (descrito nos cenários) e os **dados** (armazenados na nuvem).

---

## Arquitetura Atual da Plataforma

A plataforma opera em uma arquitetura de automação de ponta, utilizando um banco de dados **MongoDB Atlas** na nuvem como a fonte única da verdade para toda a massa de teste.

*   **Fonte de Dados:** MongoDB Atlas (Nuvem)
*   **Princípio de Teste:** Os cenários no arquivo `.feature` atuam como um "Painel de Controle", onde uma `chave_cenario` aponta para um `_id` no MongoDB.
*   **Motor de Teste:** A lógica em Java (Step Definitions) busca dinamicamente os dados no MongoDB para executar os testes.

---

## Ideias em Aberto (Próximos Passos)

### Projeto 5: A "Torre de Controle" (Unificação dos Mocks)
*   **Status:** 💡 Ideia
*   **Descrição:** Unificar todos os servidores de mock (`Validador` e `Estação Espacial`) em um único "Super Mock" para permitir testes de fluxo de negócio entre os diferentes serviços simulados.
*   **Vantagens:** Permitiria a criação de cenários de teste de integração de ponta a ponta, respondendo a perguntas como: "Quando eu crio um cliente, o serviço de validação é chamado corretamente?".

---

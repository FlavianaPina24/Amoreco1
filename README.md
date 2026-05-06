# 🚀 Framework Avançado de Automação de Testes de API

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-43B02A?style=for-the-badge&logo=cucumber)
![Rest-Assured](https://img.shields.io/badge/Rest--Assured-API_Testing-008000?style=for-the-badge)
![WireMock](https://img.shields.io/badge/WireMock-Mocking-blue?style=for-the-badge)
![Maven](https://img.shields.io/badge/Maven-Build_Tool-C71A36?style=for-the-badge&logo=apachemaven)

Bem-vindo(a) ao meu projeto de automação de testes de API! 

Este repositório contém um framework construído do zero, focado em **performance, isolamento e resiliência**, projetado para validar integrações de sistemas complexos seguindo as melhores práticas de mercado e de Qualidade de Software (QA).

---

## ✨ Destaques e Arquitetura

Este projeto não se limita apenas ao "Caminho Feliz" (Happy Path). Ele foi arquitetado para lidar com o mundo real do desenvolvimento de software:

*   **🛡️ Testes de Contrato (Contract Testing):** Utilização do `JsonSchemaValidator` para garantir que as respostas da API não quebrem as estruturas previamente acordadas.
*   **🛑 Testes de Resiliência (Sad Paths):** Suíte dedicada a validar como a API lida com erros críticos como `401 Unauthorized` (falta de token), `404 Not Found` (recursos inexistentes) e `400 Bad Request` (payloads malformados).
*   **🎭 Isolamento com WireMock:** Dependência zero de serviços externos! O framework levanta servidores dinâmicos usando `WireMock` para simular APIs. Isso garante que os testes não quebrem por falhas de rede de terceiros e estejam sempre prontos para pipelines de CI/CD.
*   **⚡ Performance com Java 21:** Uso dos modernos `Text Blocks` (`"""`) do Java 21 para centralização de massas de dados. Eliminação completa da necessidade de bancos de dados locais, mantendo o framework leve e extremamente rápido (execução de dezenas de cenários em poucos segundos).
*   **📊 Evidências Modernas:** Geração automatizada de dashboards em HTML usando `Masterthought Cucumber Reporting`, facilitando a leitura por gestores e pessoas não-técnicas.

---

## 🛠️ Tecnologias Utilizadas

*   **Linguagem:** Java 21 (JDK)
*   **Gerenciamento:** Apache Maven
*   **BDD / Core:** Cucumber (`.feature`)
*   **Requisições & Validações:** Rest-Assured
*   **Mock de Serviços:** WireMock
*   **Relatórios:** Masterthought

---

## 🚀 Como Executar o Projeto

### Pré-requisitos
*   Java JDK 21 instalado e configurado nas variáveis de ambiente.
*   Maven instalado.
*   Uma IDE de sua preferência (IntelliJ IDEA, VS Code ou Eclipse).

### Passo a Passo

1. Faça o clone deste repositório:
   ```bash
   git clone https://github.com/SEU_USUARIO/SEU_REPOSITORIO.git
   ```

2. Abra o projeto na sua IDE. O Maven fará o download de todas as dependências automaticamente.

3. Para evitar bugs comuns de execução do Windows/IDE e garantir a geração da evidência HTML, a execução é centralizada em uma única classe controladora. Na sua IDE, localize o arquivo abaixo e clique em **Run** (ou `Run Java`):
   
   👉 `src/test/java/br/com/eagro/api/Executor.java`

### Analisando os Relatórios (Dashboard)

Logo após a execução da classe `Executor`, verifique a pasta raiz do projeto. 
1. Acesse a pasta `Evidencias/`.
2. Abra a subpasta gerada com a data e hora da execução.
3. Dê um duplo clique no arquivo `overview-features.html` para abrir o Dashboard completo no seu navegador.

*(Você poderá ver gráficos detalhados, o passo a passo de cada teste e logs completos das requisições JSON mockadas).*

---

## 📖 Documentação de Bordo

Para um olhar mais aprofundado sobre os desafios superados e as decisões arquiteturais tomadas durante o desenvolvimento (incluindo as refatorações para Java 21), confira o nosso Diário de Bordo e Relatórios Técnicos.

---

👨‍💻 **Desenvolvido por Flaviana Pina**  
*Especialista em Qualidade de Software (QA) e Business Analyst*  
🔗 Visite meu Portfólio

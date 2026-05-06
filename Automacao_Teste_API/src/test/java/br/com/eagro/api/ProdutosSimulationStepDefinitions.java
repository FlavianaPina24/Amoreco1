package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class ProdutosSimulationStepDefinitions {

    private final TestContext testContext;

    public ProdutosSimulationStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para buscar todas as simulações")
    public void que_o_sistema_esta_configurado_para_buscar_todas_as_simulacoes() {
        String responseBody = "[{\"simulacaoId\": 1}, {\"simulacaoId\": 2}]";

        stubFor(get(urlPathEqualTo("/produtos-simulation/api/v2/simulation"))
                .withQueryParam("status-future-use", equalTo("true"))
                .withQueryParam("x-agro-user-id", equalTo("732.265.520-66"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para gravar uma simulação")
    public void que_o_sistema_esta_configurado_para_gravar_uma_simulacao() {
        String requestBody = """
                {
                  "codigoInstituicaoFinanceira": 237,
                  "modalidadeCredito": "RURA",
                  "codigoModalidadeCredito": "1019",
                  "numeroCPFCNPJ": "63809121185",
                  "numeroSimulacao": 25413251,
                  "valorLimite": 2409599.11
                }"""; // Body simplificado para o teste

        String responseBody = "{\"mensagem\":\"Simulação gravada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-simulation/api/v2/simulation"))
                .willReturn(aResponse()
                        .withStatus(201)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        testContext.getScenario().attach(requestBody, "application/json", "Request Body");

        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(requestBody);
        testContext.setRequest(request);
    }

    @Given("que o sistema está configurado para buscar a simulação com ID {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_simulacao_com_id(String id) {
        String responseBody = "{\"numeroSimulacao\": " + id + "}";

        stubFor(get(urlEqualTo("/produtos-simulation/api/v2/simulation/" + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para atualizar a simulação com ID {string}")
    public void que_o_sistema_esta_configurado_para_atualizar_a_simulacao_com_id(String id) {
        String requestBody = """
                {
                  "id": "68f8e22fd681f409c94e671a",
                  "numeroSimulacao": %s
                }""".formatted(id); // Body simplificado

        String responseBody = "{\"mensagem\":\"Simulação atualizada com sucesso\"}";

        stubFor(put(urlEqualTo("/produtos-simulation/api/v2/simulation/" + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        testContext.getScenario().attach(requestBody, "application/json", "Request Body");

        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .body(requestBody);
        testContext.setRequest(request);
    }
}

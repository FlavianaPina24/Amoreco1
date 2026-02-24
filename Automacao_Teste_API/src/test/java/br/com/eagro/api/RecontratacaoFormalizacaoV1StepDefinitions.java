package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class RecontratacaoFormalizacaoV1StepDefinitions {

    private final TestContext testContext;

    public RecontratacaoFormalizacaoV1StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para criar uma ordem de recontratação V1")
    public void que_o_sistema_esta_configurado_para_criar_uma_ordem_de_recontratacao_v1() {
        String requestBody = "{\"modalidadeCredito\": \"CPRF\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Ordem criada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-recontratacao-formalizacao/api/v1/order"))
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
}

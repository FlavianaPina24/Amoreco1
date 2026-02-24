package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class OrderTimelineStepDefinitions {

    private final TestContext testContext;

    public OrderTimelineStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para criar steps de formalização")
    public void que_o_sistema_esta_configurado_para_criar_steps_de_formalizacao() {
        String requestBody = "{\"numeroPedido\": 0}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Steps criados com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-timeline/api/v1/formalization/steps"))
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

    @Given("que o sistema está configurado para buscar o step da ordem {string}")
    public void que_o_sistema_esta_configurado_para_buscar_o_step_da_ordem(String orderNumber) {
        String responseBody = "{\"numeroPedido\": \"" + orderNumber + "\"}";

        stubFor(get(urlEqualTo("/produtos-order-timeline/api/v1/formalization/step/" + orderNumber))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

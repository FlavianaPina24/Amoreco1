package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class OrderTimelineV1StepDefinitions {

    private final TestContext testContext;

    public OrderTimelineV1StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para construir a timeline da ordem {string}")
    public void que_o_sistema_esta_configurado_para_construir_a_timeline_da_ordem(String orderId) {
        String requestBody = "{\"orderId\": \"" + orderId + "\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Timeline construída com sucesso\"}";

        stubFor(put(urlEqualTo("/produtos-order-timeline-orch/api/v1/timeline/build/" + orderId))
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

    @Given("que o sistema está configurado para reportar uma tarefa complementar")
    public void que_o_sistema_esta_configurado_para_reportar_uma_tarefa_complementar() {
        String requestBody = "{\"numeroPedido\": \"900908\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Tarefa reportada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-timeline-orch/api/v1/timeline/report-complementary-task"))
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

    @Given("que o sistema está configurado para buscar a timeline da ordem {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_timeline_da_ordem(String orderId) {
        String responseBody = "{\"id\": \"" + orderId + "\"}";

        stubFor(get(urlEqualTo("/produtos-order-timeline-orch/api/v1/timeline/" + orderId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para criar um parâmetro de deadline")
    public void que_o_sistema_esta_configurado_para_criar_um_parametro_de_deadline() {
        String requestBody = "{\"id\": \"63aafd01201b88b79a4162c7\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Parâmetro criado com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-timeline-orch/api/v1/parameters/deadline"))
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

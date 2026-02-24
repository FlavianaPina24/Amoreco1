package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class OrderReviewV1StepDefinitions {

    private final TestContext testContext;

    public OrderReviewV1StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para buscar a revisão da ordem {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_revisao_da_ordem(String orderNumber) {
        String responseBody = "{\"numeroPedido\": \"" + orderNumber + "\"}";

        stubFor(get(urlEqualTo("/produtos-order-review-step-orch/api/v1/revision/" + orderNumber))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para atualizar a revisão da ordem {string}")
    public void que_o_sistema_esta_configurado_para_atualizar_a_revisao_da_ordem(String orderNumber) {
        String requestBody = "{\"statusRevisao\": \"PROPOSTA_ACEITA\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Revisão atualizada com sucesso\"}";

        stubFor(put(urlEqualTo("/produtos-order-review-step-orch/api/v1/revision/" + orderNumber))
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

    @Given("que o sistema está configurado para criar uma revisão de ordem")
    public void que_o_sistema_esta_configurado_para_criar_uma_revisao_de_ordem() {
        String requestBody = "{\"numeroCPFCNPJ\": \"85257867052\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Revisão criada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-review-step-orch/api/v1/revision"))
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

    @Given("que o sistema está configurado para enviar uma notificação")
    public void que_o_sistema_esta_configurado_para_enviar_uma_notificacao() {
        String requestBody = "{\"orderId\": \"68fb93d7279f247c708d59eb\"}";

        String responseBody = "{\"mensagem\":\"Notificação enviada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-review-step-orch/api/v1/notification"))
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

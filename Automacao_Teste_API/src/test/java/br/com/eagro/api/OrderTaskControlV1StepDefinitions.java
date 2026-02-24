package br.com.eagro.api;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class OrderTaskControlV1StepDefinitions {

    public OrderTaskControlV1StepDefinitions() {
    }

    @Given("que o sistema está configurado para buscar a análise de tarefa da ordem {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_analise_de_tarefa_da_ordem(String orderId) {
        String responseBody = "{\"orderId\": \"" + orderId + "\"}";

        stubFor(get(urlEqualTo("/produtos-order-task-control/api/v1/task-analysis/" + orderId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

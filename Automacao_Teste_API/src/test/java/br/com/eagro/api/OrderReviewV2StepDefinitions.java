package br.com.eagro.api;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class OrderReviewV2StepDefinitions {

    public OrderReviewV2StepDefinitions() {
    }

    @Given("que o sistema está configurado para buscar a revisão V2 da ordem {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_revisao_v2_da_ordem(String orderNumber) {
        String responseBody = "{\"numeroPedido\": \"" + orderNumber + "\"}";

        stubFor(get(urlEqualTo("/produtos-order-review-step-orch/api/v2/revision/" + orderNumber))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

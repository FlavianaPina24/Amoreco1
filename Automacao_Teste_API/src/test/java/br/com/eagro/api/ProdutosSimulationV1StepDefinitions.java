package br.com.eagro.api;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class ProdutosSimulationV1StepDefinitions {

    public ProdutosSimulationV1StepDefinitions() {
    }

    @Given("que o sistema está configurado para buscar todas as simulações V1")
    public void que_o_sistema_esta_configurado_para_buscar_todas_as_simulacoes_v1() {
        String responseBody = "[{\"simulacaoId\": 1}]";

        stubFor(get(urlEqualTo("/produtos-simulation/api/v1/simulation"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

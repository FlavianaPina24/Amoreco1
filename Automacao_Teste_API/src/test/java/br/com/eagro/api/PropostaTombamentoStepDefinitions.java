package br.com.eagro.api;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class PropostaTombamentoStepDefinitions {

    public PropostaTombamentoStepDefinitions() {
    }

    @Given("que o sistema está configurado para o teste de tombamento da proposta {int}")
    public void que_o_sistema_esta_configurado_para_o_teste_de_tombamento_da_proposta(int idProposta) {
        String responseBody = "{\"mensagem\":\"Proposta " + idProposta + " encontrada com sucesso\"}";

        stubFor(get(urlEqualTo("/proposta/" + idProposta))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

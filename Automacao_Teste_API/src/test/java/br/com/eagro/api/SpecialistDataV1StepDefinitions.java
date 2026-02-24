package br.com.eagro.api;

import io.cucumber.java.en.Given;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

public class SpecialistDataV1StepDefinitions {

    public SpecialistDataV1StepDefinitions() {
    }

    @Given("que o sistema está configurado para buscar clientes por ID de cliente")
    public void que_o_sistema_esta_configurado_para_buscar_clientes_por_id_de_cliente() {
        String responseBody = "[{\"clientId\": 1}]";

        stubFor(get(urlEqualTo("/specialist-specialist-data-orch/api/v1/specialists/clients/by-client-id"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para deletar um cliente por ID")
    public void que_o_sistema_esta_configurado_para_deletar_um_cliente_por_id() {
        stubFor(delete(urlEqualTo("/specialist-specialist-data-orch/api/v1/specialists/clients/by-client-id"))
                .willReturn(aResponse()
                        .withStatus(204)));
    }

    @Given("que o sistema está configurado para buscar o documento do cliente da agência {string} e conta {string}")
    public void que_o_sistema_esta_configurado_para_buscar_o_documento_do_cliente_da_agencia_e_conta(String agency, String account) {
        String responseBody = "{\"cpfCnpj\": \"12345678900\"}";

        stubFor(get(urlEqualTo("/specialist-specialist-data-orch/api/v1/specialists/clients/" + agency + "/" + account))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para buscar novos clientes por nome")
    public void que_o_sistema_esta_configurado_para_buscar_novos_clientes_por_nome() {
        String responseBody = "[{\"name\": \"Caom\"}]";

        stubFor(get(urlPathEqualTo("/specialist-specialist-data-orch/api/v1/specialists/new-clients/by-name"))
                .withQueryParam("name", equalTo("Caom"))
                .withQueryParam("page", equalTo("1"))
                .withQueryParam("perPage", equalTo("10"))
                .withQueryParam("includeSavedFlag", equalTo("true"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para buscar a ordem de especialista {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_ordem_de_especialista(String orderNumber) {
        String responseBody = "{\"orderNumber\": \"" + orderNumber + "\"}";

        stubFor(get(urlEqualTo("/specialist-specialist-data-orch/api/v1/specialists/order/by-order-number/" + orderNumber))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

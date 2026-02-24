package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class RecontratacaoFormalizacaoStepDefinitions {

    private final TestContext testContext;

    public RecontratacaoFormalizacaoStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para iniciar um fluxo de preenchimento")
    public void que_o_sistema_esta_configurado_para_iniciar_um_fluxo_de_preenchimento() {
        String responseBody = "{\"mensagem\":\"Fluxo iniciado com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/orderFromOffer/0000"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para buscar a recontratação com ID {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_recontratacao_com_id(String id) {
        String responseBody = "{\"id\": \"" + id + "\", \"status\": \"ENCONTRADO\"}";

        stubFor(get(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/byId/" + id))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para atualizar a recontratação com ID {string}")
    public void que_o_sistema_esta_configurado_para_atualizar_a_recontratacao_com_id(String id) {
        String requestBody = "{\"id\": \"" + id + "\", \"versao\": \"2\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Recontratação atualizada com sucesso\"}";

        stubFor(put(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/byId/" + id))
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

    @Given("que o sistema está configurado para buscar recontratações por ID de usuário")
    public void que_o_sistema_esta_configurado_para_buscar_recontratacoes_por_id_de_usuario() {
        String responseBody = "[{\"id\": \"1\"}, {\"id\": \"2\"}]";

        stubFor(get(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/byUserId"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para atualizar a recontratação com dados do Agrotools para a oferta {string}")
    public void que_o_sistema_esta_configurado_para_atualizar_a_recontratacao_com_dados_do_agrotools_para_a_oferta(String offerId) {
        String requestBody = "{\"validacaoImovelAgrotools\": \"string\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Dados do Agrotools atualizados com sucesso\"}";

        stubFor(put(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/rehiring/agrotools/" + offerId))
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

    @Given("que o sistema está configurado para buscar a ordem com número de referência {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_ordem_com_numero_de_referencia(String refNumber) {
        String responseBody = "{\"numeroPedido\": \"" + refNumber + "\"}";

        stubFor(get(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/order/byReferenceNumber/" + refNumber))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para validar a ordem {string}")
    public void que_o_sistema_esta_configurado_para_validar_a_ordem(String orderId) {
        String responseBody = "{\"mensagem\":\"Ordem válida\"}";

        stubFor(get(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/order/" + orderId + "/validation"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para atualizar o custeio da ordem {string} a partir da oferta {string}")
    public void que_o_sistema_esta_configurado_para_atualizar_o_custeio_da_ordem_a_partir_da_oferta(String orderId, String offerId) {
        String requestBody = "{\"orcamento\": {}}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Custeio atualizado com sucesso\"}";

        stubFor(put(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/order/" + orderId + "/fromRehiring/" + offerId + "/costing"))
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

    @Given("que o sistema está configurado para criar uma simulação a partir da oferta {string}")
    public void que_o_sistema_esta_configurado_para_criar_uma_simulacao_a_partir_da_oferta(String offerId) {
        String responseBody = "{\"mensagem\":\"Simulação criada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-order-recontratacao-formalizacao-orch/api/v1/simulationFromOffer/" + offerId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }
}

package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class UserManagementDelegationV1StepDefinitions {

    private final TestContext testContext;

    public UserManagementDelegationV1StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para buscar delegações pelo email {string}")
    public void que_o_sistema_esta_configurado_para_buscar_delegacoes_pelo_email(String email) {
        String responseBody = "[{\"emailSpecialist\": \"" + email + "\"}]";
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);

        stubFor(get(urlEqualTo("/user-management-delegation-orch/api/v1/delegations/by-email-specialist/" + encodedEmail))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para delegar usuários especialistas")
    public void que_o_sistema_esta_configurado_para_delegar_usuarios_especialistas() {
        String requestBody = "{\"sourceUserEmail\": \"origem@email.com\", \"targetUserEmail\": \"destino@email.com\"}";

        String responseBody = "{\"mensagem\":\"Delegação realizada com sucesso\"}";

        stubFor(post(urlEqualTo("/user-management-delegation-orch/api/v1/specialists/users/for-delegation"))
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

    @Given("que o sistema está configurado para buscar o cliente {string} para o especialista {string}")
    public void que_o_sistema_esta_configurado_para_buscar_o_cliente_para_o_especialista(String clientId, String specialistId) {
        String responseBody = "{\"name\": \"Cliente " + clientId + "\"}";

        stubFor(get(urlEqualTo("/user-management-delegation-orch/api/v1/specialists/client/for-delegation/" + specialistId + "/" + clientId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para buscar ordens para delegação pelo email {string}")
    public void que_o_sistema_esta_configurado_para_buscar_ordens_para_delegacao_pelo_email(String email) {
        String responseBody = "[{\"orderNumber\": 1}]";
        String encodedEmail = URLEncoder.encode(email, StandardCharsets.UTF_8);

        stubFor(get(urlEqualTo("/user-management-delegation-orch/api/v1/specialists/orders/for-delegation/by-email-specialist/" + encodedEmail))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para revogar delegações em lote")
    public void que_o_sistema_esta_configurado_para_revogar_delegacoes_em_lote() {
        String responseBody = "{\"mensagem\":\"Delegações revogadas com sucesso\"}";

        stubFor(post(urlEqualTo("/user-management-delegation-orch/api/v1/batch-revokes"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para criar uma delegação")
    public void que_o_sistema_esta_configurado_para_criar_uma_delegacao() {
        String requestBody = "{\"emailSpecialistIdOrigin\": \"origem@email.com\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Delegação criada com sucesso\"}";

        stubFor(post(urlEqualTo("/user-management-delegation-orch/api/v1/delegation"))
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

    @Given("que o sistema está configurado para buscar a delegação {string}")
    public void que_o_sistema_esta_configurado_para_buscar_a_delegacao(String delegationId) {
        String responseBody = "{\"id\": \"" + delegationId + "\"}";

        stubFor(get(urlEqualTo("/user-management-delegation-orch/api/v1/delegation/" + delegationId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para atualizar a delegação {string}")
    public void que_o_sistema_esta_configurado_para_atualizar_a_delegacao(String delegationId) {
        String requestBody = "{\"id\": \"" + delegationId + "\"}"; // Body simplificado

        String responseBody = "{\"mensagem\":\"Delegação atualizada com sucesso\"}";

        stubFor(put(urlEqualTo("/user-management-delegation-orch/api/v1/delegation/" + delegationId))
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

    @Given("que o sistema está configurado para deletar a delegação {string}")
    public void que_o_sistema_esta_configurado_para_deletar_a_delegacao(String delegationId) {
        stubFor(delete(urlEqualTo("/user-management-delegation-orch/api/v1/delegation/" + delegationId))
                .willReturn(aResponse()
                        .withStatus(204)));
    }
}

package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class ResilienciaErrosStepDefinitions {

    private final TestContext testContext;

    public ResilienciaErrosStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que a API de simulação de erros está configurada")
    public void que_a_api_de_simulacao_de_erros_esta_configurada() {
        // Mock para 401 Unauthorized
        stubFor(get(urlEqualTo("/api/protegida"))
                .willReturn(aResponse()
                        .withStatus(401)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"mensagem\": \"Não autorizado: Token ausente ou inválido\"}")));

        // Mock para 404 Not Found
        stubFor(get(urlEqualTo("/api/clientes/9999999"))
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"mensagem\": \"Recurso não encontrado no servidor\"}")));

        // Mock para 400 Bad Request
        stubFor(post(urlEqualTo("/api/clientes"))
                .willReturn(aResponse()
                        .withStatus(400)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{\"mensagem\": \"Bad Request: Estrutura do JSON inválida\"}")));
    }

    @When("uma requisição {string} é enviada para o endpoint {string} sem token")
    public void uma_requisicao_sem_token(String metodo, String endpoint) {
        // Requisição intencionalmente sem header de Authorization
        Response response = given().header("Content-Type", "application/json").when().get(endpoint);
        testContext.setResponse(response);
        testContext.getScenario().attach("Requisição enviada propositalmente sem header de Autorização para validar falha de segurança.", "text/plain", "Info de Segurança");
    }

    @When("uma requisição {string} com payload malformado é enviada para {string}")
    public void uma_requisicao_payload_malformado(String metodo, String endpoint) {
        // JSON quebrado: faltando aspas, vírgulas e chaves de fechamento
        String payloadMalformado = "{\n  \"nome\": \"Cliente Teste\",\n  \"idade\": 30\n";
        
        testContext.getScenario().attach(payloadMalformado, "application/json", "Request Body Malformado (Inválido)");
        Response response = given().header("Content-Type", "application/json").body(payloadMalformado).when().post(endpoint);
        testContext.setResponse(response);
    }
}
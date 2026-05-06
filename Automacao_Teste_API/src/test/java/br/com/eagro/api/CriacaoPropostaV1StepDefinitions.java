package br.com.eagro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class CriacaoPropostaV1StepDefinitions {

    private final TestContext testContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public CriacaoPropostaV1StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que o sistema está configurado para a API de Proposta V1")
    public void que_o_sistema_esta_configurado_para_a_api_de_proposta_v1() {
        // A configuração agora é feita centralmente no Hooks.java
    }

    @When("uma requisição {string} é enviada para o endpoint {string} com a chave de cenário {string}")
    public void uma_requisicao_e_enviada_para_o_endpoint_com_a_chave_de_cenario(String metodo, String endpoint, String chaveCenario) throws IOException {
        String dadosBase = getPayloadForCenario(chaveCenario);

        if (dadosBase == null) {
            throw new RuntimeException("Cenário '" + chaveCenario + "' não encontrado nos Text Blocks.");
        }

        // Anexa o corpo da requisição ao relatório
        testContext.getScenario().attach(dadosBase, "application/json", "Request Body");

        int statusToReturn;
        String messageToReturn;
        String responseBody;

        switch (chaveCenario) {
            case "PROPOSTA_V1_CRIACAO_VALIDA":
                statusToReturn = 201;
                messageToReturn = "Proposta recebida com sucesso";
                Long propostaId = ((Number) objectMapper.readValue(dadosBase, Map.class).get("nPdidoPlatf")).longValue();
                responseBody = objectMapper.writeValueAsString(Map.of(
                    "mensagem", messageToReturn,
                    "nPdidoPlatf", propostaId
                ));
                break;
            case "PROPOSTA_V1_CPF_CURTO":
                statusToReturn = 400;
                messageToReturn = "CPF/CNPJ inválido: quantidade de dígitos incorreta";
                responseBody = objectMapper.writeValueAsString(Map.of("mensagem", messageToReturn));
                break;
            case "PROPOSTA_V1_CNPJ_ESPECIAL":
                statusToReturn = 400;
                messageToReturn = "CPF/CNPJ inválido: caracteres especiais não permitidos";
                responseBody = objectMapper.writeValueAsString(Map.of("mensagem", messageToReturn));
                break;
            default:
                statusToReturn = 500;
                messageToReturn = "Erro interno no mock: cenário não implementado";
                responseBody = objectMapper.writeValueAsString(Map.of("mensagem", messageToReturn));
                break;
        }

        stubFor(post(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withStatus(statusToReturn)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));

        Map<String, String> headers = new HashMap<>(testContext.getHeaders());
        headers.put("x-request-id", UUID.randomUUID().toString());

        RequestSpecification request = given()
                .headers(headers)
                .body(dadosBase);

        Response response = request.when().post(endpoint);

        testContext.setResponse(response);
    }

    // Novo método que substitui o MongoDB usando o poder do Java 21
    private String getPayloadForCenario(String cenarioId) {
        return switch (cenarioId) {
            case "PROPOSTA_V1_CRIACAO_VALIDA" -> """
                    {
                      "nPdidoPlatf": 54321,
                      "numeroCPFCNPJ": "63809121185"
                    }""";
            case "PROPOSTA_V1_CPF_CURTO" -> """
                    {
                      "nPdidoPlatf": 54322,
                      "numeroCPFCNPJ": "12345678"
                    }""";
            case "PROPOSTA_V1_CNPJ_ESPECIAL" -> """
                    {
                      "nPdidoPlatf": 54323,
                      "numeroCPFCNPJ": "12.345.678/0001-9@"
                    }""";
            default -> null;
        };
    }
}

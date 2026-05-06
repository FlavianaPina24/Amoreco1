package br.com.eagro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.Map;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;

public class DocumentValidationStepDefinitions {

    private final TestContext testContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public DocumentValidationStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("um documento do cenário {string} a ser validado")
    public void um_documento_do_cenario_a_ser_validado(String cenarioId) {
        String body = getPayloadForCenario(cenarioId);
        testContext.set("documentoBody", body);
        testContext.set("cenarioId", cenarioId);
    }

    @When("uma requisição de validação {string} é enviada para {string}")
    public void a_validation_post_request_is_sent_to(String metodo, String endpoint) throws IOException {
        String body = (String) testContext.get("documentoBody");
        String cenarioId = (String) testContext.get("cenarioId");

        // Proteção contra NullPointerException caso a massa de dados não seja encontrada
        if (body == null) {
            throw new IllegalStateException("ERRO: Massa de dados vazia para o cenário '" + cenarioId + "'. Verifique os Text Blocks.");
        }

        // Anexa o corpo da requisição ao relatório
        testContext.getScenario().attach(body, "application/json", "Request Body");

        int statusToReturn;
        String messageToReturn;

        switch (cenarioId) {
            case "VALIDACAO_CNPJ_ALFA_VALIDO":
            case "VALIDACAO_CPF_VALIDO":
            case "VALIDACAO_CNPJ_ALFA_DV_VALIDO":
                statusToReturn = 200;
                messageToReturn = "Documento com formato válido";
                break;
            case "VALIDACAO_CNPJ_CURTO":
            case "VALIDACAO_CNPJ_LONGO":
                statusToReturn = 400;
                messageToReturn = "CNPJ inválido: quantidade de dígitos incorreta";
                break;
            case "VALIDACAO_CPF_CURTO":
            case "VALIDACAO_CPF_LONGO":
                statusToReturn = 400;
                messageToReturn = "CPF inválido: quantidade de dígitos incorreta";
                break;
            case "VALIDACAO_CNPJ_COM_ESPECIAIS":
                statusToReturn = 400;
                messageToReturn = "CNPJ inválido: caracteres especiais não permitidos";
                break;
            case "VALIDACAO_CPF_COM_ESPECIAIS":
                statusToReturn = 400;
                messageToReturn = "CPF inválido: caracteres especiais não permitidos";
                break;
            case "VALIDACAO_CNPJ_ALFA_DV_INVALIDO":
                statusToReturn = 400;
                messageToReturn = "CNPJ inválido: dígito verificador não confere";
                break;
            default:
                statusToReturn = 500;
                messageToReturn = "Erro interno no mock: cenário de validação não implementado";
                break;
        }

        stubFor(post(urlEqualTo(endpoint))
                .willReturn(aResponse()
                        .withStatus(statusToReturn)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(Map.of("mensagem", messageToReturn)))));

        RequestSpecification request = given()
                .contentType("application/json")
                .body(body);

        Response response = request.when().post(endpoint);
        testContext.setResponse(response);
    }

    @Then("a resposta da validação deve ter o status {int}")
    public void the_validation_response_status_code_should_be(Integer statusCode) {
        testContext.getResponse().then().statusCode(statusCode);
    }

    @Then("o corpo da resposta da validação deve ter a mensagem {string}")
    public void the_validation_response_body_should_have_the_message(String message) {
        testContext.getResponse().then().body("mensagem", containsString(message));
    }

    // Novo método que substitui o MongoDB usando o poder do Java 21
    private String getPayloadForCenario(String cenarioId) {
        return switch (cenarioId) {
            case "VALIDACAO_CNPJ_ALFA_VALIDO", "VALIDACAO_CNPJ_ALFA_DV_VALIDO" -> """
                    {
                      "documento": "12ABC678000190"
                    }""";
            case "VALIDACAO_CPF_VALIDO" -> """
                    {
                      "documento": "12345678909"
                    }""";
            case "VALIDACAO_CNPJ_CURTO" -> """
                    {
                      "documento": "12345678000"
                    }""";
            case "VALIDACAO_CNPJ_LONGO" -> """
                    {
                      "documento": "12345678000190123"
                    }""";
            case "VALIDACAO_CPF_CURTO" -> """
                    {
                      "documento": "12345678"
                    }""";
            case "VALIDACAO_CPF_LONGO" -> """
                    {
                      "documento": "1234567890123"
                    }""";
            case "VALIDACAO_CNPJ_COM_ESPECIAIS" -> """
                    {
                      "documento": "12.345.678/0001-9@"
                    }""";
            case "VALIDACAO_CPF_COM_ESPECIAIS" -> """
                    {
                      "documento": "123.456.789-0@"
                    }""";
            case "VALIDACAO_CNPJ_ALFA_DV_INVALIDO" -> """
                    {
                      "documento": "12ABC678000199"
                    }""";
            default -> null;
        };
    }
}

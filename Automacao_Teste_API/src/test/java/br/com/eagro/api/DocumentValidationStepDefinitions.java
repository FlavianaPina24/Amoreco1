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
        String body = MongoConnection.getCenarioBody(cenarioId, "cenarios");
        testContext.set("documentoBody", body);
        testContext.set("cenarioId", cenarioId);
    }

    @When("uma requisição de validação {string} é enviada para {string}")
    public void a_validation_post_request_is_sent_to(String metodo, String endpoint) throws IOException {
        String body = (String) testContext.get("documentoBody");
        String cenarioId = (String) testContext.get("cenarioId");

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
}

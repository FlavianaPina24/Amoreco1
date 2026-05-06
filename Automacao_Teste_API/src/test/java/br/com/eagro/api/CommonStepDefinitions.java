package br.com.eagro.api;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.notNullValue;

public class CommonStepDefinitions {

    private final TestContext testContext;

    public CommonStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Then("a resposta deve ter o status {int}")
    public void a_resposta_deve_ter_o_status(Integer statusCode) {
        testContext.getResponse().then().statusCode(statusCode);
    }

    @Then("a resposta deve conter a mensagem {string}")
    public void a_resposta_deve_conter_a_mensagem(String message) {
        testContext.getResponse().then().body("mensagem", org.hamcrest.Matchers.equalTo(message));
    }

    @When("uma requisição {string} é enviada para o endpoint {string}")
    public void uma_requisicao_e_enviada_para_o_endpoint(String method, String endpoint) {
        RequestSpecification request = testContext.getRequest();
        if (request == null) {
            request = io.restassured.RestAssured.given();
        }
        Response response;

        switch (method.toLowerCase()) {
            case "post":
                response = request.when().post(endpoint);
                break;
            case "put":
                response = request.when().put(endpoint);
                break;
            case "get":
                response = request.when().get(endpoint);
                break;
            case "delete":
                response = request.when().delete(endpoint);
                break;
            default:
                throw new IllegalArgumentException("Método HTTP inválido: " + method);
        }
        testContext.setResponse(response);
    }

    @When("uma requisição {string} é enviada para o endpoint {string} com os parâmetros")
    public void uma_requisicao_e_enviada_para_o_endpoint_com_os_parametros(String method, String endpoint, DataTable params) {
        RequestSpecification request = testContext.getRequest();
        if (request == null) {
            request = io.restassured.RestAssured.given();
        }

        Map<String, String> queryParams = params.asMap(String.class, String.class);
        request.queryParams(queryParams);

        uma_requisicao_e_enviada_para_o_endpoint(method, endpoint);
    }

    @Then("a resposta deve conter a propriedade {string}")
    public void a_resposta_deve_conter_a_propriedade(String property) {
        testContext.getResponse().then().body(property, notNullValue());
    }

    @Then("a resposta deve respeitar o contrato do schema {string}")
    public void a_resposta_deve_respeitar_o_contrato_do_schema(String schemaFileName) {
        testContext.getResponse().then().assertThat().body(matchesJsonSchemaInClasspath("schemas/" + schemaFileName));
    }
}

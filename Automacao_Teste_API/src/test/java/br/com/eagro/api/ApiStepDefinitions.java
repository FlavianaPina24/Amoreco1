package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiStepDefinitions {

    private final TestContext testContext;

    public ApiStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("the request body is:")
    public void the_request_body_is(String docString) {
        // Armazena e anexa o corpo da requisição ao relatório
        testContext.setRequestBody(docString);
        testContext.getScenario().attach(docString, "application/json", "Request Body");

        RequestSpecification request = testContext.getRequest().header("Content-Type", "application/json").body(docString);
        testContext.setRequest(request);
    }

    private void sendRequest(String method, String endpoint) {
        RequestSpecification request = testContext.getRequest();
        Response response;

        System.out.println("\n[DIAGNÓSTICO REST-ASSURED] Detalhes da Requisição:");
        request.log().all();

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

    @When("a POST request is sent to {string}")
    public void a_post_request_is_sent_to(String endpoint) {
        sendRequest("post", endpoint);
    }

    @When("a PUT request is sent to {string}")
    public void a_put_request_is_sent_to(String endpoint) {
        sendRequest("put", endpoint);
    }

    @When("a GET request is sent to {string}")
    public void a_get_request_is_sent_to(String endpoint) {
        sendRequest("get", endpoint);
    }

    @When("a DELETE request is sent to {string}")
    public void a_delete_request_is_sent_to(String endpoint) {
        sendRequest("delete", endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(Integer statusCode) {
        testContext.getResponse().then().statusCode(statusCode);
    }

    @Then("the response body should contain the property {string}")
    public void the_response_body_should_contain_the_property(String property) {
        testContext.getResponse().then().body(property, notNullValue());
    }

    @Then("the response body should have the value {string} for the property {string}")
    public void the_response_body_should_have_the_value_string_for_the_property(String value, String property) {
        testContext.getResponse().then().body(property, equalTo(value));
    }

    @Then("the response body should have the value {int} for the property {string}")
    public void the_response_body_should_have_the_value_int_for_the_property(Integer value, String property) {
        testContext.getResponse().then().body(property, equalTo(value));
    }
}

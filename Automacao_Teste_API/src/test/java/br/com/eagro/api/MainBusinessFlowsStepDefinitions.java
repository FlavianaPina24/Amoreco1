package br.com.eagro.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class MainBusinessFlowsStepDefinitions {

    private final TestContext testContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public MainBusinessFlowsStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @After("@main-flow")
    public void teardown() {
        MongoConnection.close();
    }

    @Given("que o sistema está pronto para criar um novo cliente")
    public void que_o_sistema_esta_pronto_para_criar_um_novo_cliente() {
        TestContext.mainDatabase.clear();
        TestContext.mainIdCounter = 1;
    }

    @Given("que o cliente com ID 1 existe no sistema")
    public void que_o_cliente_com_id_1_existe_no_sistema() {
        TestContext.mainDatabase.clear();
        User user = new User(1, "João Silva", null, 30, null, null, null, "123.456.789-00", null);
        TestContext.mainDatabase.put(1, user);
        TestContext.mainIdCounter = 2;
    }

    @Given("que o cliente com ID 1 foi deletado do sistema")
    public void que_o_cliente_com_id_1_foi_deletado_do_sistema() {
        TestContext.mainDatabase.clear();
        TestContext.mainIdCounter = 2;
    }

    @When("uma requisição {string} é enviada para o endpoint {string} com a chave de dados {string}")
    public void uma_requisicao_sem_regra_e_enviada(String metodo, String endpoint, String chaveDados) throws IOException {
        uma_requisicao_com_regra_e_enviada(metodo, endpoint, chaveDados, "NENHUMA");
    }

    @When("uma requisição {string} é enviada para o endpoint {string} com a chave de dados {string} e a regra {string}")
    public void uma_requisicao_com_regra_e_enviada(String metodo, String endpoint, String chaveDados, String regraMassa) throws IOException {
        Response response = null;

        String dadosBody = MongoConnection.getCenarioBody(chaveDados, "cenarios");
        if (dadosBody == null) {
            throw new RuntimeException("Cenário não encontrado no MongoDB com _id: " + chaveDados);
        }

        // Anexa o corpo da requisição ao relatório
        testContext.getScenario().attach(dadosBody, "application/json", "Request Body");

        for (User user : TestContext.mainDatabase.values()) {
            stubFor(get(urlEqualTo("/v1/clientes/" + user.getId()))
                .willReturn(aResponse().withStatus(200).withHeader("Content-Type", "application/json").withBody(objectMapper.writeValueAsString(user))));
        }
        stubFor(get(urlMatching("/v1/clientes/\\d+")).atPriority(10).willReturn(aResponse().withStatus(404).withBody("{\"mensagem\":\"Cliente não encontrado\"}")));
        stubFor(post(urlEqualTo("/v1/clientes")).willReturn(aResponse().withStatus(201).withBody("{\"mensagem\":\"Cliente criado com sucesso\"}")));
        stubFor(put(urlMatching("/v1/clientes/\\d+")).willReturn(aResponse().withStatus(200).withBody("{\"mensagem\":\"Cliente atualizado\"}")));
        stubFor(delete(urlMatching("/v1/clientes/\\d+")).willReturn(aResponse().withStatus(204)));

        RequestSpecification requestSpec = given().contentType("application/json").body(dadosBody);

        switch (metodo) {
            case "POST": response = requestSpec.when().post(endpoint); break;
            case "GET": response = given().when().get(endpoint); break;
            case "PUT": response = requestSpec.when().put(endpoint); break;
            case "DELETE": response = requestSpec.when().delete(endpoint); break;
        }
        testContext.setResponse(response);
    }

    @Then("o corpo da resposta do cliente deve conter o nome {string}")
    public void o_corpo_da_resposta_do_cliente_deve_conter_o_nome(String nomeCliente) throws IOException {
        User user = objectMapper.readValue(testContext.getResponse().getBody().asString(), User.class);
        assert user.getNome().equals(nomeCliente);
    }
}

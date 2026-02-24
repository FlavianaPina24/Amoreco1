package br.com.eagro.api;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ConsultaPropostaV1StepDefinitions {

    private final TestContext testContext;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ConsultaPropostaV1StepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que uma proposta base existe no sistema com a chave de cenário {string}")
    public void que_uma_proposta_base_existe_no_sistema_com_a_chave_de_cenario(String chaveCenario) throws IOException {
        String collectionName = (String) testContext.get("collectionName");
        String dadosBase = MongoConnection.getCenarioBody(chaveCenario, collectionName);
        if (dadosBase == null) {
            throw new RuntimeException("Cenário de consulta '" + chaveCenario + "' não encontrado na coleção '" + collectionName + "'");
        }

        Map<String, Object> propostaData = objectMapper.readValue(dadosBase, new TypeReference<>() {});
        Long propostaId = ((Number) propostaData.get("nPdidoPlatf")).longValue();
        testContext.set("baseProposalId", propostaId);

        stubFor(get(urlEqualTo("/proposta/" + propostaId))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(dadosBase)));

        stubFor(get(urlMatching("/proposta/[0-9]+"))
                .atPriority(10)
                .willReturn(aResponse()
                        .withStatus(404)
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(Map.of("mensagem", "Proposta não encontrada")))));
    }

    @Given("que o sistema está pronto para uma consulta de proposta")
    public void que_o_sistema_esta_pronto_para_uma_consulta_de_proposta() {
        // A limpeza do mock é feita no Hooks.java
    }

    @When("uma requisição {string} é enviada para o endpoint da proposta base")
    public void uma_requisicao_e_enviada_para_o_endpoint_da_proposta_base(String metodo) {
        Long propostaId = (Long) testContext.get("baseProposalId");
        if (propostaId == null) {
            throw new IllegalStateException("O ID da proposta base não foi encontrado no contexto.");
        }
        enviarRequisicaoConsulta(metodo, propostaId.toString());
    }

    @When("uma requisição {string} é enviada para o endpoint de consulta com o ID {string}")
    public void uma_requisicao_e_enviada_para_o_endpoint_de_consulta_com_o_id(String metodo, String id) {
        enviarRequisicaoConsulta(metodo, id);
    }

    private void enviarRequisicaoConsulta(String metodo, String id) {
        String endpoint = "/proposta/" + id;

        // CORREÇÃO: Usa o getter específico para garantir a tipagem correta.
        Map<String, String> headers = new HashMap<>(testContext.getHeaders());
        headers.put("x-request-id", UUID.randomUUID().toString());

        RequestSpecification request = given().headers(headers);

        Response response = request.when().get(endpoint);

        testContext.setResponse(response);
    }

    @Then("o corpo da resposta da proposta deve conter o ID {string}")
    public void o_corpo_da_resposta_da_proposta_deve_conter_o_id(String id) {
        testContext.getResponse().then().body("nPdidoPlatf", equalTo(Long.parseLong(id)));
    }

    @Then("o corpo da resposta da proposta deve conter o nome do cliente {string}")
    public void o_corpo_da_resposta_da_proposta_deve_conter_o_nome_do_cliente(String nomeCliente) {
        testContext.getResponse().then().body("cadCli.iScialCli", equalTo(nomeCliente));
    }
}

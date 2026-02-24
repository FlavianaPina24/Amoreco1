package br.com.eagro.api;

import io.cucumber.java.en.Given;
import io.restassured.specification.RequestSpecification;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.restassured.RestAssured.given;

public class ProdbbdsrvlimwpStepDefinitions {

    private final TestContext testContext;

    public ProdbbdsrvlimwpStepDefinitions(TestContext testContext) {
        this.testContext = testContext;
    }

    @Given("que eu tenho as credenciais para o serviço de Limite Climático")
    public void que_eu_tenho_as_credenciais_para_o_servico_de_limite_climatico() throws IOException {
        String responseBody = "{\"status\":\"UP\"}";

        stubFor(get(urlEqualTo("/v2/orders/health"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para cancelar uma ordem")
    public void que_o_sistema_esta_configurado_para_cancelar_uma_ordem() {
        String requestBody = "{\n" +
                "  \"cpssoaJuridContr\": 0,\n" +
                "  \"ctpoContrNegoc\": 0,\n" +
                "  \"nseqContrNegoc\": 0,\n" +
                "  \"ncontrLim\": 0,\n" +
                "  \"cprodtServcOper\": 0,\n" +
                "  \"cpssoaCsumr\": 0,\n" +
                "  \"csitSuborLim\": 0,\n" +
                "  \"cdoctoCsumr\": 0,\n" +
                "  \"ccpfCnpjCsumr\": \"YAYHIMRUKCWC80\",\n" +
                "  \"cflialCpfCnpjC\": \"0001\",\n" +
                "  \"cctrlCpfCnpjC\": \"01\",\n" +
                "  \"cdoctoPssoaEmprC\": \"string\",\n" +
                "  \"vutlzdProdtSubor\": 0,\n" +
                "  \"cindcdMoedaUtlzd\": 0,\n" +
                "  \"dthrCriacReg\": \"2025-10-09T17:18:28.985Z\",\n" +
                "  \"dthrUltAltReg\": \"2025-10-09T17:18:28.985Z\",\n" +
                "  \"cusuCriacReg\": \"string\",\n" +
                "  \"cusuUltAltReg\": \"string\"\n" +
                "}";

        String responseBody = "{\"mensagem\":\"Ordem cancelada com sucesso\"}";

        stubFor(post(urlEqualTo("/v2/orders/backoffice/cancel"))
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

    @Given("que o sistema está configurado para consultar o status de uma ordem")
    public void que_o_sistema_esta_configurado_para_consultar_o_status_de_uma_ordem() {
        String responseBody = "{\"status\":\"APROVADO\"}";

        stubFor(get(urlEqualTo("/v2/orders/backoffice/80834236000162/status"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(responseBody)));
    }

    @Given("que o sistema está configurado para criar uma ordem")
    public void que_o_sistema_esta_configurado_para_criar_uma_ordem() {
        String requestBody = "{\n" +
                "  \"ccsist\": \"AGRO\",\n" +
                "  \"cpssoaJuridSensi\": 0,\n" +
                "  \"ctpoContrSensi\": 0,\n" +
                "  \"nseqContrSensi\": 0,\n" +
                "  \"cpssoaJuridContr\": 0,\n" +
                "  \"ctpoContrNegoc\": 0,\n" +
                "  \"nseqContrNegoc\": 0,\n" +
                "  \"ncontrLimProdt\": 0,\n" +
                "  \"cprodtServcOper\": 0,\n" +
                "  \"cpssoa\": 0,\n" +
                "  \"ccatlgGarnt\": 0,\n" +
                "  \"ctpoCoobcProdt\": 0,\n" +
                "  \"ctpoRecProdt\": 0,\n" +
                "  \"cutilzSpreadTx\": 0,\n" +
                "  \"cpssoaCsumr\": 0,\n" +
                "  \"ctpoPssoaCsumr\": \"string\",\n" +
                "  \"cdoctoCsumrLim\": 0,\n" +
                "  \"ccpfCnpjCsumrLim\": \"41564768971006\",\n" +
                "  \"cflialCpfCnpjCsu\": \"0001\",\n" +
                "  \"cctrlCpfCnpjCsum\": \"01\",\n" +
                "  \"cdoctoPssoaEmprC\": \"string\",\n" +
                "  \"pspreadProdt\": 0,\n" +
                "  \"cindcdUndSpread\": 0,\n" +
                "  \"cundOrgnz\": 0,\n" +
                "  \"cpssoaJuridOrgnz\": 0,\n" +
                "  \"qtdAvalista\": 0,\n" +
                "  \"grupoAvalista\": [\n" +
                "    {\n" +
                "      \"cdoctoAvals\": 0,\n" +
                "      \"ccpfCnpjAval\": \"41564768971006\",\n" +
                "      \"cflialCpfCnpjAva\": \"0001\",\n" +
                "      \"cctrlCpfCnpjAval\": \"01\",\n" +
                "      \"cdoctoPssoaEmprA\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"dthrCriacReg\": \"2025-10-09T17:15:59.351Z\",\n" +
                "  \"dthrUltAltReg\": \"2025-10-09T17:15:59.351Z\",\n" +
                "  \"cusuCriacReg\": \"string\",\n" +
                "  \"cusuUltAltReg\": \"string\"\n" +
                "}";

        String responseBody = "{\"mensagem\":\"Ordem criada com sucesso\"}";

        stubFor(post(urlEqualTo("/v2/orders/backoffice"))
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

    @Given("que o sistema está configurado para cancelar uma ordem V1")
    public void que_o_sistema_esta_configurado_para_cancelar_uma_ordem_v1() {
        String requestBody = "{\n" +
                "  \"cpssoaJuridContr\": 0,\n" +
                "  \"ctpoContrNegoc\": 0,\n" +
                "  \"nseqContrNegoc\": 0,\n" +
                "  \"ncontrLim\": 0,\n" +
                "  \"cprodtServcOper\": 0,\n" +
                "  \"cpssoaCsumr\": 0,\n" +
                "  \"csitSuborLim\": 0,\n" +
                "  \"cdoctoCsumr\": 0,\n" +
                "  \"ccpfCnpjCsumr\": 0,\n" +
                "  \"cflialCpfCnpjC\": 0,\n" +
                "  \"cctrlCpfCnpjC\": 0,\n" +
                "  \"cdoctoPssoaEmprC\": \"string\",\n" +
                "  \"vutlzdProdtSubor\": 0,\n" +
                "  \"cindcdMoedaUtlzd\": 0\n" +
                "}";

        String responseBody = "{\"mensagem\":\"Ordem V1 cancelada com sucesso\"}";

        stubFor(post(urlEqualTo("/produtos-bbd-agro-srv-limite-clim-wrapper/api/v1/order/backoffice/cancel"))
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
}

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
        String requestBody = """
                {
                  "cpssoaJuridContr": 0,
                  "ctpoContrNegoc": 0,
                  "nseqContrNegoc": 0,
                  "ncontrLim": 0,
                  "cprodtServcOper": 0,
                  "cpssoaCsumr": 0,
                  "csitSuborLim": 0,
                  "cdoctoCsumr": 0,
                  "ccpfCnpjCsumr": "YAYHIMRUKCWC80",
                  "cflialCpfCnpjC": "0001",
                  "cctrlCpfCnpjC": "01",
                  "cdoctoPssoaEmprC": "string",
                  "vutlzdProdtSubor": 0,
                  "cindcdMoedaUtlzd": 0,
                  "dthrCriacReg": "2025-10-09T17:18:28.985Z",
                  "dthrUltAltReg": "2025-10-09T17:18:28.985Z",
                  "cusuCriacReg": "string",
                  "cusuUltAltReg": "string"
                }""";

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
        String requestBody = """
                {
                  "ccsist": "AGRO",
                  "cpssoaJuridSensi": 0,
                  "ctpoContrSensi": 0,
                  "nseqContrSensi": 0,
                  "cpssoaJuridContr": 0,
                  "ctpoContrNegoc": 0,
                  "nseqContrNegoc": 0,
                  "ncontrLimProdt": 0,
                  "cprodtServcOper": 0,
                  "cpssoa": 0,
                  "ccatlgGarnt": 0,
                  "ctpoCoobcProdt": 0,
                  "ctpoRecProdt": 0,
                  "cutilzSpreadTx": 0,
                  "cpssoaCsumr": 0,
                  "ctpoPssoaCsumr": "string",
                  "cdoctoCsumrLim": 0,
                  "ccpfCnpjCsumrLim": "41564768971006",
                  "cflialCpfCnpjCsu": "0001",
                  "cctrlCpfCnpjCsum": "01",
                  "cdoctoPssoaEmprC": "string",
                  "pspreadProdt": 0,
                  "cindcdUndSpread": 0,
                  "cundOrgnz": 0,
                  "cpssoaJuridOrgnz": 0,
                  "qtdAvalista": 0,
                  "grupoAvalista": [
                    {
                      "cdoctoAvals": 0,
                      "ccpfCnpjAval": "41564768971006",
                      "cflialCpfCnpjAva": "0001",
                      "cctrlCpfCnpjAval": "01",
                      "cdoctoPssoaEmprA": "string"
                    }
                  ],
                  "dthrCriacReg": "2025-10-09T17:15:59.351Z",
                  "dthrUltAltReg": "2025-10-09T17:15:59.351Z",
                  "cusuCriacReg": "string",
                  "cusuUltAltReg": "string"
                }""";

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
        String requestBody = """
                {
                  "cpssoaJuridContr": 0,
                  "ctpoContrNegoc": 0,
                  "nseqContrNegoc": 0,
                  "ncontrLim": 0,
                  "cprodtServcOper": 0,
                  "cpssoaCsumr": 0,
                  "csitSuborLim": 0,
                  "cdoctoCsumr": 0,
                  "ccpfCnpjCsumr": 0,
                  "cflialCpfCnpjC": 0,
                  "cctrlCpfCnpjC": 0,
                  "cdoctoPssoaEmprC": "string",
                  "vutlzdProdtSubor": 0,
                  "cindcdMoedaUtlzd": 0
                }""";

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

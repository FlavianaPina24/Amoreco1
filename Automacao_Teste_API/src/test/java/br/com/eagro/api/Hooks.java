package br.com.eagro.api;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Hooks {

    private final TestContext testContext;

    public Hooks(TestContext testContext) {
        this.testContext = testContext;
    }

    @BeforeAll
    public static void beforeAll() {
        TestContext.startMockServer();
        System.out.println("HOOK @BeforeAll: Servidor WireMock iniciado em porta dinâmica.");
    }

    @AfterAll
    public static void afterAll() {
        TestContext.stopMockServer();
        System.out.println("HOOK @AfterAll: Servidor WireMock parado.");
        ReportGenerator.generateReport();
    }

    @Before
    public void setupScenario(Scenario scenario) {
        testContext.setScenario(scenario);

        if (TestContext.getWireMockServer() != null && TestContext.getWireMockServer().isRunning()) {
            WireMock.configureFor("localhost", TestContext.getWireMockServer().port());
            WireMock.reset();
        }

        Collection<String> tags = scenario.getSourceTagNames();
        boolean isExternalApiTest = tags.stream().noneMatch(tag -> tag.startsWith("@"));

        if (isExternalApiTest || tags.contains("@jsonplaceholder")) { 
            RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
            RestAssured.port = 443;
            RequestSpecification request = given().relaxedHTTPSValidation();
            testContext.setRequest(request);
        } else {
            RestAssured.baseURI = "http://localhost";
            RestAssured.port = TestContext.getWireMockServer().port();
            RequestSpecification request = given();
            testContext.setRequest(request);
        }

        if (tags.contains("@api-proposta-v1")) {
            String apiTag = "api-proposta-v1";
            String collectionName = ConfigLoader.getProperty(apiTag + ".collection");
            testContext.set("collectionName", collectionName);

            Map<String, String> headers = new HashMap<>();
            headers.put("Content-Type", "application/json");
            headers.put("Accept", "*/*");
            headers.put("x-stateless-open", ConfigLoader.getProperty("x_stateless_open"));
            headers.put("x-stateless-closed", ConfigLoader.getProperty("x_stateless_closed"));
            testContext.setHeaders(headers);
        }
    }
}

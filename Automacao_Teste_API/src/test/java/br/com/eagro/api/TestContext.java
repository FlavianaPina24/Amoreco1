package br.com.eagro.api;

import com.github.tomakehurst.wiremock.WireMockServer;
import io.cucumber.java.Scenario;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;

public class TestContext {

    private static WireMockServer wireMockServer;
    private Response response;
    private RequestSpecification request;
    private String requestBody;
    private Map<String, String> headers;
    private Scenario scenario; // Campo para armazenar o cenário atual
    private final Map<String, Object> context = new HashMap<>();

    public static Map<Integer, User> mainDatabase = new HashMap<>();
    public static int mainIdCounter = 1;

    public static void startMockServer() {
        if (wireMockServer == null || !wireMockServer.isRunning()) {
            wireMockServer = new WireMockServer(options().dynamicPort());
            wireMockServer.start();
        }
    }

    public static void stopMockServer() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
        }
    }

    public static WireMockServer getWireMockServer() {
        return wireMockServer;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public Response getResponse() {
        return response;
    }

    public void setRequest(RequestSpecification request) {
        this.request = request;
    }

    public RequestSpecification getRequest() {
        return request;
    }

    public void setRequestBody(String body) {
        this.requestBody = body;
    }

    public String getRequestBody() {
        return requestBody;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public Scenario getScenario() {
        return scenario;
    }

    public void set(String key, Object value) {
        context.put(key, value);
    }

    public Object get(String key) {
        return context.get(key);
    }

    public void setRequestMethod(String method) {
        set("requestMethod", method);
    }

    public void setRequestUrl(String url) {
        set("requestUrl", url);
    }
}

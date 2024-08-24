package com.moondog.labs.bible;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.http.Body;
import com.github.tomakehurst.wiremock.matching.UrlPathPattern;
import java.util.Map;
import java.util.function.Function;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

public class EasyRequestResponseBuilder {
    private HttpMethod method;
    private String urlMatchPattern;
    private Map<String, Object> responseBody;
    private HttpStatus status;
    private final ObjectMapper objectMapper;

    public EasyRequestResponseBuilder(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EasyRequestResponseBuilder method(HttpMethod method) {
        this.method = method;
        return this;
    }

    public EasyRequestResponseBuilder urlMatchPattern(String urlMatchPattern) {
        this.urlMatchPattern = urlMatchPattern;
        return this;
    }

    public EasyRequestResponseBuilder responseBody(Class<?> clazz, Map<String, Object> responseBody) {
        this.responseBody = responseBody;
        return this;
    }

    public EasyRequestResponseBuilder responseStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public void build() {
        stubFor(methodMapping
                .get(method)
                .apply(getUrlPathPattern())
                .willReturn(WireMock.aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withResponseBody(getResponseBody())
                        .withStatus(status.value())));
    }

    private UrlPathPattern getUrlPathPattern() {
        return WireMock.urlPathMatching(urlMatchPattern);
    }

    private Body getResponseBody() {
        try {
            return new Body(objectMapper.writeValueAsString(responseBody));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static final Map<HttpMethod, Function<UrlPathPattern, MappingBuilder>> methodMapping = Map.of(
            HttpMethod.GET, WireMock::get,
            HttpMethod.POST, WireMock::post,
            HttpMethod.PUT, WireMock::put,
            HttpMethod.DELETE, WireMock::delete);
}

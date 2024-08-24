package com.moondog.labs.bible;

import static com.github.tomakehurst.wiremock.client.WireMock.configureFor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
public class TestBase {
    private static final WireMockServer wireMockServer = new WireMockServer();

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        wireMockServer.start();
        configureFor("localhost", 8080);
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }

    protected ResultActions executeGet(final String url) {
        return execute(MockMvcRequestBuilders.get(url));
    }

    protected ResultActions execute(final MockHttpServletRequestBuilder mockHttpServletRequestBuilder) {
        try {
            return mockMvc.perform(mockHttpServletRequestBuilder);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected EasyRequestResponseBuilder easyRequestResponseBuilder() {
        return new EasyRequestResponseBuilder(objectMapper);
    }
}

package com.moondog.labs.bible;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathMatching;
import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BibleControllerTest extends TestBase {
    @Test
    void getVerse() throws Exception {
        stubFor(get(urlPathMatching(".*Romans%208:37.*"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withResponseBody(asBody(Instancio.of(Passage.class)
                                .set(field(Passage.class, "reference"), "Romans 8:37")
                                .set(
                                        field(Passage.class, "verses"),
                                        List.of(Instancio.of(Verse.class)
                                                .set(field(Verse.class, "text"), "No in all these things . . .")
                                                .create()))
                                .create()))
                        .withStatus(200)));

        execute(MockMvcRequestBuilders.get("/bible/Romans/8/37"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.reference").value("Romans 8:37"),
                        jsonPath("$.verses[0].text").value("No in all these things . . ."));
    }
}

package com.moondog.labs.bible;

import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class BibleControllerTest extends TestBase {
    @Test
    void getVerse() throws Exception {
        easyRequestResponseBuilder(HttpMethod.GET, ".*Romans%208:37.*")
                .withResponseBody(Instancio.of(Passage.class)
                        .set(field(Passage::getReference), "Romans 8:37")
                        .set(
                                field(Passage::getVerses),
                                Instancio.ofList(Verse.class)
                                        .set(field(Verse::getText), "No in all these things . . .")
                                        .create())
                        .create())
                .withStatus(HttpStatus.OK)
                .build();

        executeGet("/bible/Romans/8/37")
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.reference").value("Romans 8:37"),
                        jsonPath("$.verses[0].text").value("No in all these things . . ."));
    }
}

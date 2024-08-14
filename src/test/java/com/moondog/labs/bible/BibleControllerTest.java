package com.moondog.labs.bible;

import static org.instancio.Select.field;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import org.instancio.Instancio;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

class BibleControllerTest extends TestBase {
    @Test
    void getVerse() throws Exception {
        easyRequestResponseBuilder()
                .withMethod(HttpMethod.GET)
                .withUrlMatchPattern(".*Romans%208:37.*")
                .withResponseBody(Instancio.of(Passage.class)
                        .set(field(Passage.class, "reference"), "Romans 8:37")
                        .set(
                                field(Passage.class, "verses"),
                                List.of(Instancio.of(Verse.class)
                                        .set(field(Verse.class, "text"), "No in all these things . . .")
                                        .create()))
                        .create())
                .withStatus(HttpStatus.OK)
                .build();

        execute(MockMvcRequestBuilders.get("/bible/Romans/8/37"))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.reference").value("Romans 8:37"),
                        jsonPath("$.verses[0].text").value("No in all these things . . ."));
    }
}

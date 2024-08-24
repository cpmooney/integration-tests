package com.moondog.labs.bible;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;

class BibleControllerTest extends TestBase {
    @Test
    void getVerse() throws Exception {
        easyRequestResponseBuilder()
                .method(HttpMethod.GET)
                .urlMatchPattern(".*Romans%208:37.*")
                .responseBody(
                        Passage.class,
                        Map.of(
                                "getReference",
                                "Romans 8:37",
                                "getVerses",
                                List.of(Map.of("getText", "No in all these things . . ."))))
                .responseStatus(HttpStatus.OK)
                .build();

        executeGet("/bible/Romans/8/37")
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.reference").value("Romans 8:37"),
                        jsonPath("$.verses[0].text").value("No in all these things . . ."));
    }
}

package com.moondog.labs.bible;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "bible-client", url = "${bible-api.url}")
public interface BibleClient {
    @GetMapping("{book}%20{chapter}:{verse}")
    Passage getVerse(@PathVariable String book, @PathVariable String chapter, @PathVariable String verse);
}

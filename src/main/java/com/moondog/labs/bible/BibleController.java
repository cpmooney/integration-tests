package com.moondog.labs.bible;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BibleController {
    private final BibleClient bibleClient;

    @GetMapping("/bible/{book}/{chapter}/{verse}")
    public Passage getVerse(@PathVariable String book, @PathVariable String chapter, @PathVariable String verse) {
        return bibleClient.getVerse(book, chapter, verse);
    }
}

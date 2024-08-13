package com.moondog.labs.bible;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Passage {
    private String reference;
    private List<Verse> verses;
    private String text;
    private String translationId;
    private String translationName;
    private String translationNote;
}

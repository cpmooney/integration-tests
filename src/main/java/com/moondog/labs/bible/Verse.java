package com.moondog.labs.bible;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Verse {
    private String bookId;
    private String bookName;
    private String chapter;
    private String verse;
    private String text;
}

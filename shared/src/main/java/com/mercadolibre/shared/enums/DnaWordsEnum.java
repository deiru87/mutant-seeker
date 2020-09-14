package com.mercadolibre.shared.enums;


/**
 * Enum where we have the sequence available in dna
 *
 */
public enum DnaWordsEnum {
    AAAA("AAAA","AAAA"),
    CCCC("CCCC","CCCC"),
    GGGG("GGGG","GGGG"),
    TTTT("TTTT","TTTT");

    private String word;
    private String desc;

    /**
     * Constructor Enum to handler sequence of dna
     * @param word
     * @param desc
     */
    DnaWordsEnum(String word, String desc) {
        this.word = word;
        this.desc = desc;
    }


    public String getWord() {
        return word;
    }

    public String getDesc() {
        return desc;
    }
}

package com.holyking.simtra.model;

public class Sentence {
    protected int indexInArticle;

    protected String sentence;

    public int getIndexInArticle() {
        return indexInArticle;
    }

    public void setIndexInArticle(int indexInArticle) {
        this.indexInArticle = indexInArticle;
    }

    public String getSentence() {
        return sentence;
    }

    public void setSentence(String sentence) {
        this.sentence = sentence;
    }

    public Sentence(int indexInArticle, String sentence) {
        this.indexInArticle = indexInArticle;
        this.sentence = sentence;
    }

    public Sentence() {
    }
}

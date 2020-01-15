package com.holyking.simtra.model;

import java.util.List;

public class WrongSentence extends Sentence {

    private List<Integer> wrongIndexList;

    private List<Character> wrongWords;

    private List<List<Character>> chooseList;

    public List<Integer> getWrongIndexList() {
        return wrongIndexList;
    }

    public void setWrongIndexList(List<Integer> wrongIndexList) {
        this.wrongIndexList = wrongIndexList;
    }

    public List<Character> getWrongWords() {
        return wrongWords;
    }

    public void setWrongWords(List<Character> wrongWords) {
        this.wrongWords = wrongWords;
    }

    public List<List<Character>> getChooseList() {
        return chooseList;
    }

    public void setChooseList(List<List<Character>> chooseList) {
        this.chooseList = chooseList;
    }

    public WrongSentence(int indexInArticle,
                         String sentence,
                         List<Integer> wrongIndexList,
                         List<Character> wrongWords,
                         List<List<Character>> chooseList) {
        this.indexInArticle = indexInArticle;
        this.sentence = sentence;
        this.wrongIndexList = wrongIndexList;
        this.wrongWords = wrongWords;
        this.chooseList = chooseList;
    }
}

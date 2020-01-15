package com.holyking.simtra.service;

import com.holyking.simtra.constant.Config;
import com.holyking.simtra.constant.Words;
import com.holyking.simtra.model.Article;
import com.holyking.simtra.model.RightSentence;
import com.holyking.simtra.model.Sentence;
import com.holyking.simtra.model.WrongSentence;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SimToTra{
    public Article change(String[] text) {
        char[] wordsArray;
        List<Character> chooseWordsList;
        List<RightSentence> completedList = new ArrayList<>();
        List<WrongSentence> needComfirmList = new ArrayList<>();
        Article article = new Article();
        List<Integer> wrongIndexList = new ArrayList<>();
        List<Character> wrongWords = new ArrayList<>();
        List<List<Character>> chooseList = new ArrayList<>();
        WrongSentence sentenceModel;
        for (int index = 0; index < text.length; index ++) {
            wordsArray = text[index].toCharArray();
            for (int i = 0; i < wordsArray.length; i ++) {
                if (Words.WORDS_KEY.contains(wordsArray[i])) {
                    chooseWordsList = Words.WORDS_MAP.get(wordsArray[i]);
                    if (chooseWordsList.size() == 1) {
                        wordsArray[i] = chooseWordsList.get(0);
                    } else {
                        chooseList.add(chooseWordsList);
                        wrongWords.add(wordsArray[i]);
                        wrongIndexList.add(i);
                    }
                }
            }
            if (!wrongIndexList.isEmpty()) {
                sentenceModel = new WrongSentence(index,
                        new String(wordsArray), wrongIndexList, wrongWords, chooseList);
                needComfirmList.add(sentenceModel);
                wrongIndexList = new ArrayList<>();
                wrongWords = new ArrayList<>();
                chooseList = new ArrayList<>();
            } else {
                completedList.add(new RightSentence(index, new String(wordsArray)));
            }
        }
        article.setCompletedList(completedList);
        article.setNeedComfirmList(needComfirmList);
        return article;
    }

    public Sentence change(String sentence) {
        String[] sentences = new String[]{sentence};
        if (!change(sentences).getCompletedList().isEmpty())
            return change(sentences).getCompletedList().get(0);
        return change(sentences).getNeedComfirmList().get(0);
    }

    public Article change(File file) {
        FileReader fileReader;
        BufferedReader bufferedReader;
        List<String> list = new ArrayList<>();
        String sentence;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            while((sentence = bufferedReader.readLine()) != null) {
                if(sentence.trim().length() > 2) {
                    list.add(sentence);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("file is not exist");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IO Exception");
        }
        if (!list.isEmpty()) {
            String[] sentences = new String[list.size()];
            list.toArray(sentences);
            return change(sentences);
        }
        return null;
    }

    public List<RightSentence> solveWrong(Article article) {
        int wrongCount = 0;
        List<WrongSentence> wrongSentencelist = article.getNeedComfirmList();
        for (WrongSentence wrongSentence: wrongSentencelist) {
            wrongCount += wrongSentence.getWrongIndexList().size();
        }
        List<Integer> chosenList = new ArrayList<>();
        for (int i = 1; i <= wrongCount; i ++)
            chosenList.add(0);
        return solveWrong(article, chosenList);
    }

    public List<RightSentence> solveWrong(Article article, List<Integer> chosenList) {
        List<WrongSentence> needComfirmList = article.getNeedComfirmList();
        List<RightSentence> completedlist = article.getCompletedList();
        List<RightSentence> comfirmedlist = new ArrayList<>();
        char[] currentSentenceValue;
        List<Integer> wrongIndexList;
        String currentRightSentence;
        for (WrongSentence wrongSentence : needComfirmList) {
            currentSentenceValue = wrongSentence.getSentence().toCharArray();
            wrongIndexList = wrongSentence.getWrongIndexList();
            for (Integer wrongIndex : wrongIndexList) {
                currentSentenceValue[wrongIndex] =
                        Words.WORDS_MAP.get(currentSentenceValue[wrongIndex]).get(chosenList.get(0));
                chosenList.remove(0);
            }
            currentRightSentence = new String(currentSentenceValue);
            comfirmedlist.add(new RightSentence(wrongSentence.getIndexInArticle(), currentRightSentence));
        }
        completedlist.addAll(comfirmedlist);
        completedlist.sort(Comparator.comparingInt(Sentence::getIndexInArticle));
        return completedlist;
    }

    public void write(List<RightSentence> sentenceList, String path) throws IOException{
        if (null == path || "".equals(path.trim()))
            path = Config.DEFAULT_OUTPUT_PATH;
        List<String> sentence = new ArrayList<>();
        for (RightSentence rightSentence: sentenceList)
            sentence.add(rightSentence.getSentence());
        Files.write(Paths.get(path), sentence);
    }
}

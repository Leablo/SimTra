package com.holyking.simtra.model;

import java.util.List;

public class Article {
    private List<RightSentence> completedList;

    private List<WrongSentence> needComfirmList;

    public List<RightSentence> getCompletedList() {
        return completedList;
    }

    public void setCompletedList(List<RightSentence> completedList) {
        this.completedList = completedList;
    }

    public List<WrongSentence> getNeedComfirmList() {
        return needComfirmList;
    }

    public void setNeedComfirmList(List<WrongSentence> needComfirmList) {
        this.needComfirmList = needComfirmList;
    }
}

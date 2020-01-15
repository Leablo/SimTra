package com.holyking.simtra;

import com.holyking.simtra.model.Article;
import com.holyking.simtra.model.RightSentence;
import com.holyking.simtra.service.SimToTra;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Test {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        SimToTra stt = new SimToTra();
        File file = new File("I:/congcong.txt");
        Article result = stt.change(file);
        List<RightSentence> list = stt.solveWrong(result);
        try {
            stt.write(list, null);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("something wrong happens when writing file");
        }
        long endTime = System.currentTimeMillis();
        System.out.println("用时："+ (endTime - startTime));
    }
}

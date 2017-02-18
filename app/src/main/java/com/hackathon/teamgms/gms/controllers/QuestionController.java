package com.hackathon.teamgms.gms.controllers;

import android.util.Log;

import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.teamgms.gms.models.Question;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello_DE on 2017-02-18.
 */

public class QuestionController {
    private static final String TAG = QuestionController.class.getSimpleName();

    public static void createQuestion(String userId, String question, String choice1, String choice2, String choice3, String choice4, Long choice1Count, Long choice2Count, Long choice3Count, Long choice4Count, Boolean isEnd, String endTIme, Long endCount) {

        Question newQuestion = new Question(userId, question, choice1, choice2, choice3, choice4, choice1Count, choice2Count, choice3Count, choice4Count, isEnd, endTIme, endCount);
        final Map<String, Object> newQuestionValues = newQuestion.makeQuestionMap();

        Map<String, Object> newQuestionMap = new HashMap<>();
        newQuestionMap.put("/questions/question", newQuestionValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(newQuestionMap);
    }

    public static void updateChoice(String questionNum, int choice, Long chCount) {
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("userId", "");
        updateValues.put("question", "질문");
        updateValues.put("choice1", "1번 선택지");
        updateValues.put("choice2", "2번 선택지");
        updateValues.put("choice3", "3번 선택지");
        updateValues.put("choice4", "4번 선택지");
        updateValues.put("isEnd", true);
        updateValues.put("endTime", "");
        updateValues.put("endCount", 0);

        Log.d("abc-u", chCount+"");
        if(choice == 1) {
            updateValues.put("choice2Count", 0);
            updateValues.put("choice3Count", 0);
            updateValues.put("choice4Count", 0);
            updateValues.put("choice1Count", new Long(chCount));
        }
            if(choice == 2) {
                updateValues.put("choice1Count", 0);
                updateValues.put("choice3Count", 0);
                updateValues.put("choice4Count", 0);
                updateValues.put("choice2Count", new Long(chCount));
            } if(choice == 3) {
            updateValues.put("choice1Count", 0);
            updateValues.put("choice2Count", 0);
            updateValues.put("choice4Count", 0);
            updateValues.put("choice3Count", new Long(chCount));
        }
         if(choice == 4) {
             updateValues.put("choice1Count", 0);
             updateValues.put("choice2Count", 0);
             updateValues.put("choice3Count", 0);
             updateValues.put("choice4Count", new Long(chCount));
         }
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/questions/" + questionNum, updateValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void updateEnd(String questionNum, Question question) {
        Map<String, Object> updateValues = question.makeQuestionMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/questions/" + questionNum, updateValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }
}

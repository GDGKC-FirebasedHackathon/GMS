package com.hackathon.teamgms.gms.controllers;

import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.teamgms.gms.models.Question;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello_DE on 2017-02-18.
 */

public class QuestionController {
    private static final String TAG = QuestionController.class.getSimpleName();

    public static void createQuestion(String userId, String question, String choice1, String choice2, String choice3, String choice4, Boolean isEnd, String endTIme, Long endCount) {

        Question newQuestion = new Question(userId, question, choice1, choice2, choice3, choice4, isEnd, endTIme, endCount);
        final Map<String, Object> newQuestionValues = newQuestion.makeQuestionMap();

        Map<String, Object> newQuestionMap = new HashMap<>();
        newQuestionMap.put("/questions/question", newQuestionValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(newQuestionMap);
    }

    public static void updateChoice(String questionNum, int choice, int chCount) {
        Map<String, Object> updateValues = new HashMap<>();
        if(choice == 1)
            updateValues.put("choice1Count", chCount);
        else if(choice == 2)
            updateValues.put("choice2Count", chCount);
        else if(choice == 3)
            updateValues.put("choice3Count", chCount);
        else if(choice == 4)
            updateValues.put("choic42Count", chCount);
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

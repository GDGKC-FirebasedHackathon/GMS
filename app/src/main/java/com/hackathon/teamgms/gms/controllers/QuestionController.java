package com.hackathon.teamgms.gms.controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.hackathon.teamgms.gms.models.Question;
import com.hackathon.teamgms.gms.models.QuestionCount;

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

    public static void updateEnd(String questionNum) {
        Map<String, Object> updateValues = new HashMap<>();;
        updateValues.put("isEnd", "true");
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/questions/" + questionNum, updateValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }
}

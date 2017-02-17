package com.hackathon.teamgms.gms.controllers;

import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hello_DE on 2017-02-18.
 */

public class QuestionController {
    private static final String TAG = QuestionController.class.getSimpleName();

    public static void updateEnd(String questionNum) { //
        Map<String, Object> updateValues = new HashMap<>();
        updateValues.put("isEnd", "true");
        FirebaseDatabase.getInstance().getReference().child("questions").child(questionNum).updateChildren(updateValues);
    }
}

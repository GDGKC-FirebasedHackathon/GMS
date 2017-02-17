package com.hackathon.teamgms.gms.models;

import com.google.firebase.database.DataSnapshot;

import java.util.HashMap;
import java.util.Map;

public class Question {
    public String userId;
    public String question;

    public String choice1;
    public String choice2;
    public String choice3;
    public String choice4;

    public Long choice1Count;
    public Long choice2Count;
    public Long choice3Count;
    public Long choice4Count;

    public Boolean isEnd;
    public String endTime;
    public Long endCount;

    public Question() {
    }

    public Question(String userId, String question, String choice1, String choice2, String choice3, String choice4, Boolean isEnd, String endTime, Long endCount) {
        this.userId = userId;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.isEnd = isEnd;
        this.endTime = endTime;
        this.endCount = endCount;
    }

    public Question(String userId, String question, String choice1, String choice2, String choice3, String choice4, Long choice1Count, Long choice2Count, Long choice3Count, Long choice4Count, Boolean isEnd, String endTime, Long endCount) {
        this.userId = userId;
        this.question = question;
        this.choice1 = choice1;
        this.choice2 = choice2;
        this.choice3 = choice3;
        this.choice4 = choice4;
        this.choice1Count = choice1Count;
        this.choice2Count = choice2Count;
        this.choice3Count = choice3Count;
        this.choice4Count = choice4Count;
        this.isEnd = isEnd;
        this.endTime = endTime;
        this.endCount = endCount;
    }

    public Map<String, Object> makeQuestionMap() {
        HashMap<String, Object> result = new HashMap<String, Object>();

        result.put("userId", userId);
        result.put("question", question);
        result.put("choice1", choice1);
        result.put("choice2", choice2);
        result.put("choice3", choice3);
        result.put("choice4", choice4);
        result.put("isEnd", isEnd);
        result.put("endTime", endTime);
        result.put("endCount", endCount);

        return result;
    }

    public static Question parseQuestionSnapshot(DataSnapshot dataSnapshot) {
        Question question = new Question();

        question.userId = (String)dataSnapshot.child("userId").getValue();
        question.question = (String)dataSnapshot.child("question").getValue();
        question.choice1 = (String)dataSnapshot.child("choice1").getValue();
        question.choice2 = (String)dataSnapshot.child("choice2").getValue();
        question.choice3 = (String)dataSnapshot.child("choice3").getValue();
        question.choice4 = (String)dataSnapshot.child("choice4").getValue();
        question.choice1Count = (Long)dataSnapshot.child("choice1Count").getValue();
        question.choice2Count = (Long)dataSnapshot.child("choice2Count").getValue();
        question.choice3Count = (Long)dataSnapshot.child("choice3Count").getValue();
        question.choice4Count = (Long)dataSnapshot.child("choice4Count").getValue();
        question.isEnd = (Boolean) dataSnapshot.child("isEnd").getValue();
        question.endTime = (String)dataSnapshot.child("endTime").getValue();
        question.endCount = (Long)dataSnapshot.child("endCount").getValue();

        return question;
    }
}
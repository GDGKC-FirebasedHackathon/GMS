package com.hackathon.teamgms.gms.models;

import com.google.firebase.database.GenericTypeIndicator;

import java.util.List;

public class QuestionCount {
    public Long questionCount;

    public QuestionCount() {

    }
    public QuestionCount(Long questionCount) {
        this.questionCount = questionCount;
    }

    public Long getQuestionCount() {
        return questionCount;
    }
}

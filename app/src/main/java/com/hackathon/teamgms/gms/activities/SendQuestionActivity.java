package com.hackathon.teamgms.gms.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hackathon.teamgms.gms.R;
import com.hackathon.teamgms.gms.controllers.QuestionController;

public class SendQuestionActivity extends AppCompatActivity {

    private String userId;

    EditText et_question;
    EditText et_choice1;
    EditText et_choice2;
    EditText et_choice3;
    EditText et_choice4;
    EditText et_endTime;
    EditText et_endCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_question);

        et_question = (EditText)findViewById(R.id.et_question);
        et_choice1 = (EditText)findViewById(R.id.et_choice1);
        et_choice2 = (EditText)findViewById(R.id.et_choice2);
        et_choice3 = (EditText)findViewById(R.id.et_choice3);
        et_choice4 = (EditText)findViewById(R.id.et_choice4);
        et_endTime = (EditText)findViewById(R.id.et_endTime);
        et_endCount = (EditText)findViewById(R.id.et_endCount);

        //userId 가져오기
        userId = "";
    }

    public void onClick(View v) {
        if(userId == null || userId.equals("")) {
            //error
        }

        QuestionController.createQuestion(userId,
                et_question.getText().toString(),
                et_choice1.getText().toString(),
                et_choice2.getText().toString(),
                et_choice3.getText().toString(),
                et_choice4.getText().toString(),
                new Boolean(false),
                et_endTime.getText().toString(),
                Long.parseLong(et_endCount.getText().toString()));

        //화면 전환 및 토스트 출력
        Toast.makeText(this, "Send Question Test", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, HomeActivity.class);
        finish();
    }
}

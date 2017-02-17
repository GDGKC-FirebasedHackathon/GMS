package com.hackathon.teamgms.gms.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioGroup;

import com.hackathon.teamgms.gms.R;

public class AnswerActivity extends AppCompatActivity {

    private RadioGroup radioGroup_answer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        radioGroup_answer = (RadioGroup)findViewById(R.id.radioGroup_answer);
        radioGroup_answer.setOnCheckedChangeListener(choiceChange);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    protected RadioGroup.OnCheckedChangeListener choiceChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId) {
                case R.id.rb_one :

                    break;
                case R.id.rb_two :

                    break;
                case R.id.rb_three :

                    break;
                case R.id.rb_four :

                    break;
            }
        }
    };

    private void updatetInterface() {

    }

    public void onClick(View v) {
        if(v.getId() == R.id.btn_send) {

        }
    }
}

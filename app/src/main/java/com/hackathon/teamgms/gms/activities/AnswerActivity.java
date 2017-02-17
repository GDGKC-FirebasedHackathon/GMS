package com.hackathon.teamgms.gms.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.teamgms.gms.R;
import com.hackathon.teamgms.gms.models.Question;

public class AnswerActivity extends AppCompatActivity {
    private final String TAG = AnswerActivity.class.getSimpleName();

    private RadioGroup radioGroup_answer;
    private RadioButton rb_one;
    private RadioButton rb_two;
    private RadioButton rb_three;
    private RadioButton rb_four;
    private TextView tv_chooseQ;
    private int check;

    private DatabaseReference mReference;
    private ValueEventListener mReadListner;

    Question chkQuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        radioGroup_answer = (RadioGroup)findViewById(R.id.radioGroup_answer);
        radioGroup_answer.setOnCheckedChangeListener(choiceChange);

        rb_one = (RadioButton)findViewById(R.id.rb_one);
        rb_two = (RadioButton)findViewById(R.id.rb_two);
        rb_three = (RadioButton)findViewById(R.id.rb_three);
        rb_four = (RadioButton)findViewById(R.id.rb_four);

        tv_chooseQ = (TextView)findViewById(R.id.tv_chooseQ);

        check = 0;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mReference = database.getReference("questions");
    }


    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener readListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    chkQuest =  Question.parseQuestionSnapshot(child);
                    if(chkQuest.isEnd)
                        continue;
                    else
                        break;
                }

                updatetInterface();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadUserList:onCancelled", databaseError.toException());
            }
        };
        if(mReference != null) {
            mReference.addListenerForSingleValueEvent(readListener);
        }
        mReadListner = readListener;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    protected RadioGroup.OnCheckedChangeListener choiceChange = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch(checkedId) {
                case R.id.rb_one :
                    check = 1;
                    break;
                case R.id.rb_two :
                    check = 2;
                    break;
                case R.id.rb_three :
                    check = 3;
                    break;
                case R.id.rb_four :
                    check = 4;
                    break;
            }
        }
    };

    private void updatetInterface() {
        tv_chooseQ.setText(chkQuest.question);
        rb_one.setText(chkQuest.choice1);
        rb_two.setText(chkQuest.choice2);
        rb_three.setText(chkQuest.choice3);
        rb_four.setText(chkQuest.choice4);
    }

    public void onClick(View v) {
        if(v.getId() == R.id.btn_send) {
            if(check != 0) {
                onUpdateData(mReference);
            }
        }
    }

    private void onUpdateData(DatabaseReference postRef) {
        postRef.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                chkQuest = mutableData.getValue(Question.class);
                if (chkQuest == null) {
                    return Transaction.success(mutableData);
                }

                if(check == 1) chkQuest.choice1Count++;
                else if(check == 2) chkQuest.choice2Count++;
                else if(check == 3) chkQuest.choice3Count++;
                else if(check == 4) chkQuest.choice4Count++;

                // Set value and report transaction success
                mutableData.setValue(chkQuest);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b,
                                   DataSnapshot dataSnapshot) {
                // Transaction completed
                Log.d(TAG, "postTransaction:onComplete:" + databaseError);
            }
        });
    }
}

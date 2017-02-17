package com.hackathon.teamgms.gms.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.teamgms.gms.R;
import com.hackathon.teamgms.gms.controllers.DBController;
import com.hackathon.teamgms.gms.controllers.QuestionController;
import com.hackathon.teamgms.gms.models.Question;

public class ResultActivity extends AppCompatActivity {
    private final String TAG = ResultActivity.class.getSimpleName();

    private DBController dbController;

    private String mFirebaseUid;
    private DatabaseReference mDataReference;
    private DatabaseReference mQuestionReference;
    private ValueEventListener mReadListner;

    private TextView tv_userQ;
    private TextView tv_userA;

    private String questionNum;

    private Long choice1Count;
    private Long choice2Count;
    private Long choice3Count;
    private Long choice4Count;

    private String question;
    private String resultAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        dbController = new DBController(this);

        tv_userQ = (TextView)findViewById(R.id.tv_userQ);
        tv_userA = (TextView)findViewById(R.id.tv_userA);

        //questionNum = dbController.readQuestionNum();

        /*
        //mFirebaseUid = UserUtil.loadUserFirebaseUid();

        if (mFirebaseUid == null || FirebaseAuth.getInstance().getCurrentUser() == null){
            mUserReference = null;
        }
        else {
            mUserReference = FirebaseDatabase.getInstance().getReference().child("users");
        }*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mQuestionReference = database.getReference("questions");

    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener readListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                DataSnapshot child = dataSnapshot.child(questionNum);

                Question chkQuest =  Question.parseQuestionSnapshot(child);

                if(!chkQuest.isEnd) {

                    question = chkQuest.question;

                    choice1Count = chkQuest.choice1Count;
                    choice2Count = chkQuest.choice2Count;
                    choice3Count = chkQuest.choice3Count;
                    choice4Count = chkQuest.choice4Count;;

                    if(choice1Count != null && choice1Count > choice2Count && choice1Count > choice3Count && choice1Count > choice4Count)
                        resultAnswer = chkQuest.choice1;
                    else if(choice2Count != null && choice2Count > choice1Count && choice2Count > choice3Count && choice2Count > choice4Count)
                        resultAnswer = chkQuest.choice2;
                    else if(choice3Count != null && choice3Count > choice1Count && choice3Count > choice2Count && choice3Count > choice4Count)
                        resultAnswer = chkQuest.choice3;
                    else if(choice4Count != null && choice4Count > choice1Count && choice4Count > choice2Count && choice4Count > choice3Count)
                        resultAnswer = chkQuest.choice4;

                }

                chkQuest.isEnd = true;
                QuestionController.updateEnd(questionNum, chkQuest);

                updateResultInterface();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadResultList:onCancelled", databaseError.toException());
            }

        };
        if(mReadListner != null) {
            mQuestionReference.addListenerForSingleValueEvent(readListner);
        }
        mReadListner = readListner;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mReadListner != null) {
            if(mQuestionReference != null) {
                mQuestionReference.removeEventListener(mReadListner);
            }
        }
    }

    private void updateResultInterface() {
        if(question != null && resultAnswer != null) {
            tv_userQ.setText(question);
            tv_userA.setText(resultAnswer);
        } else {
            tv_userQ.setText("");
            tv_userA.setText("");
        }
    }
}

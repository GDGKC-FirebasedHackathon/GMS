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

public class ResultActivity extends AppCompatActivity {
    private final String TAG = ResultActivity.class.getSimpleName();

    private String mFirebaseUid;
    private DatabaseReference mDataReference;
    private DatabaseReference mQuestionReference;
    private ValueEventListener mReadListner;

    private TextView tv_userQ;
    private TextView tv_userA;

    private String mQestionId;

    private Long countOne;
    private Long countTwo;
    private Long countThree;
    private Long countFour;

    private String qestionName;
    private String resultAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv_userQ = (TextView)findViewById(R.id.tv_userQ);
        tv_userA = (TextView)findViewById(R.id.tv_userA);

        countOne = (long)0;
        countTwo = (long)0;
        countThree = (long)0;
        countFour = (long)0;


        /*
        //mFirebaseUid = UserUtil.loadUserFirebaseUid();

        if (mFirebaseUid == null || FirebaseAuth.getInstance().getCurrentUser() == null){
            mUserReference = null;
        }
        else {
            mUserReference = FirebaseDatabase.getInstance().getReference().child("users");
        }*/

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        mDataReference = database.getReference(""); //답변트리 전체
        mQuestionReference = database.getReference(""); //질문트리에서 맞는거 찾아야해...

    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener readListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot child : dataSnapshot.getChildren()) {
                    //Boolean isRead = (Boolean)child.child("isRead").getValue();
                    String questionId = (String)child.child("questionId").getValue();
                    //if(isRead != null && isRead == false && questionId.equals(mQestionId)) {
                    if(questionId.equals(mQestionId)) {
                        Long answer = (Long) child.child("answer").getValue();
                        if(answer != null) {
                            if (answer.intValue() == 1) countOne++;
                            else if (answer.intValue() == 2) countTwo++;
                            else if (answer.intValue() == 3) countThree++;
                            else countFour++;
                        }
                    }
                }

                mQuestionReference.addListenerForSingleValueEvent( new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            qestionName = (String)child.child("questionName").getValue();
                            if(countOne > countTwo && countOne > countThree && countOne > countFour)
                                resultAnswer = (String)child.child("answerOne").getValue();
                            else if(countTwo > countOne && countTwo > countThree && countTwo > countFour)
                                resultAnswer = (String)child.child("answerTwo").getValue();
                            else if(countThree > countOne && countThree > countThree && countThree > countFour)
                                resultAnswer = (String)child.child("answerThree").getValue();
                            else
                                resultAnswer = (String)child.child("answerFour").getValue();

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.w(TAG, "loadQuestion:onCancelled", databaseError.toException());
                    }
                });

                updateResultInterface();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "loadResultList:onCancelled", databaseError.toException());
            }

        };
        if(mReadListner != null) {
            mDataReference.addListenerForSingleValueEvent(readListner);
        }
        mReadListner = readListner;
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mReadListner != null) {
            if(mDataReference != null) {
                mDataReference.removeEventListener(mReadListner);
            }
        }
    }

    public void updateResultInterface() {
        tv_userQ.setText(qestionName);
        tv_userA.setText(resultAnswer);
    }
}

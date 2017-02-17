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

import java.util.HashMap;
import java.util.Map;

public class ResultActivity extends AppCompatActivity {
    private final String TAG = ResultActivity.class.getSimpleName();

    private String mFirebaseUid;
    private DatabaseReference mDataReference;
    private DatabaseReference mQuestionReference;
    private ValueEventListener mReadListner;

    private TextView tv_userQ;
    private TextView tv_userA;

    private String mQestionId;

    private Long choiceCount1;
    private Long choiceCount2;
    private Long choiceCount3;
    private Long choiceCount4;

    private String question;
    private String resultAnswer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv_userQ = (TextView)findViewById(R.id.tv_userQ);
        tv_userA = (TextView)findViewById(R.id.tv_userA);

        //mQestionId초기화 : 내장디비에서 가져와...

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

                DataSnapshot child = dataSnapshot.child(mQestionId);

                if(!(Boolean)child.child("isEnd").getValue()) {
                    question = (String) child.child("question").getValue();

                    choiceCount1 = (Long) child.child("choiceCount1").getValue();
                    choiceCount2 = (Long) child.child("choiceCount2").getValue();
                    choiceCount3 = (Long) child.child("choiceCount3").getValue();
                    choiceCount4 = (Long) child.child("choiceCount4").getValue();

                    if(choiceCount1 != null && choiceCount1 > choiceCount2 && choiceCount1 > choiceCount3 && choiceCount1 > choiceCount4)
                        resultAnswer = question = (String) child.child("choice1").getValue();
                    else if(choiceCount2 != null && choiceCount2 > choiceCount1 && choiceCount2 > choiceCount3 && choiceCount2 > choiceCount4)
                        resultAnswer = question = (String) child.child("choice2").getValue();
                    else if(choiceCount3 != null && choiceCount3 > choiceCount1 && choiceCount3 > choiceCount2 && choiceCount3 > choiceCount4)
                        resultAnswer = question = (String) child.child("choice3").getValue();
                    else if(choiceCount4 != null && choiceCount4 > choiceCount1 && choiceCount4 > choiceCount2 && choiceCount4 > choiceCount3)
                        resultAnswer = question = (String) child.child("choice4").getValue();

                }

                //controller 안으로 넣기
                Map<String, Object> updateValues = new HashMap<>();
                updateValues.put("isEnd", "true");
                FirebaseDatabase.getInstance().getReference().child("questions").child(mQestionId).updateChildren(updateValues);


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

    public void updateResultInterface() {
        tv_userQ.setText(question);
        tv_userA.setText(resultAnswer);
    }
}

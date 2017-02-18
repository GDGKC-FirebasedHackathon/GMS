package com.hackathon.teamgms.gms.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.teamgms.gms.R;
import com.hackathon.teamgms.gms.models.User;
import com.hackathon.teamgms.gms.utils.UserUtil;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.hackathon.teamgms.gms.R;
import com.hackathon.teamgms.gms.models.Question;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private final String TAG = HomeActivity.class.getSimpleName();

    private String mFirebaseUid;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserReference;
    private User mUser;
    TextView tvName;
    TextView tvEmail;
    ImageView imgUser;
    View headerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                    updateUI();
                }
            }
        });
        // 로컬 디바이스에 저장된 FirebaseUid 획득
        mFirebaseUid = UserUtil.loadUserFirebaseUid(this);
        Log.d("uid", mFirebaseUid);

        // init firebase
        if (mFirebaseUid == null || FirebaseAuth.getInstance().getCurrentUser() == null){
            mUserReference = null;
        }
        else {
            mUserReference = FirebaseDatabase.getInstance().getReference().child("users");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
    }

    public void onClick(View v) {
        Intent intent = new Intent(this, SendQuestionActivity.class);
        startActivity(intent);
    }

    private void updateUI() {
        tvName.setText("Login 하세요");
        tvEmail.setVisibility(View.INVISIBLE);
        imgUser.setVisibility(View.INVISIBLE);
    }

    private void updateUserInterface() {
        tvName = (TextView)headerView.findViewById(R.id.tvName);
        tvEmail = (TextView)headerView.findViewById(R.id.tvEmail);
        imgUser = (ImageView)headerView.findViewById(R.id.imgUser);
        tvName.setText(mUser.userName);
        tvEmail.setText(mUser.userEmail);
        Glide.with(this).load(mUser.profilePictureUrl).into(imgUser);
    }

    @Override
    protected void onStart() {
        super.onStart();

        ValueEventListener userListner = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = User.parseSnapshot(dataSnapshot);
                if (user.isDeleted != null && user.isDeleted == false) {
                    mUser = user;
                }
                //update ui
                updateUserInterface();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "getUser:onCancelled", databaseError.toException());
            }
        };

        mUserReference.child(mFirebaseUid).addListenerForSingleValueEvent(userListner);

        final Intent intent = new Intent(this, AnswerActivity.class);

        DatabaseReference questionReference = FirebaseDatabase.getInstance().getReference().child("questions").child("question");

        ValueEventListener questionListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Question question = Question.parseQuestionSnapshot(dataSnapshot);
                if(!question.isEnd) {
                    intent.putExtra("question", question);
                Log.d("abc", question.choice1);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        questionReference.addValueEventListener(questionListener);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        } else if(id == R.id.action_logOut) {
            mAuth.signOut();

        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

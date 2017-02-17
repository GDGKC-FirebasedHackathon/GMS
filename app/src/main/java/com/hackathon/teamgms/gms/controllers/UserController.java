package com.hackathon.teamgms.gms.controllers;

import com.google.firebase.database.FirebaseDatabase;
import com.hackathon.teamgms.gms.models.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by yunjeonghwang on 2017. 2. 17..
 */

public class UserController {

    private static final String TAG = UserController.class.getSimpleName();

    public static void createUser(String firebaseUid, String userEmail, String userName, String profilePictureUrl) {
        User user = new User(firebaseUid, userEmail, userName, profilePictureUrl);

        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void editUser(String firebaseUid, Map<String, Object> updateValues) {
        FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUid).updateChildren(updateValues);
    }

    public static void editUser(String firebaseUid, User user) {
        Map<String, Object> userValues = user.toMap();
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + firebaseUid, userValues);

        FirebaseDatabase.getInstance().getReference().updateChildren(childUpdates);
    }

    public static void deleteUser(String firebaseUid, User user) {
        user.isDeleted = true;
        editUser(firebaseUid, user);
    }

}


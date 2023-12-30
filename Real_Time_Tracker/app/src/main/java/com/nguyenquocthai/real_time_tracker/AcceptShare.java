package com.nguyenquocthai.real_time_tracker;

import static android.app.PendingIntent.getActivity;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.goodiebag.pinview.Pinview;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenquocthai.real_time_tracker.Activity.MainActivity;
import com.nguyenquocthai.real_time_tracker.Model.CircleJoin;

public class AcceptShare {

    public AcceptShare(String userID, Activity myactivity) {
        this.userID = userID;
        this.myactivity = myactivity;
    }

    public void Execute(){
        initializeViews();
        currentID=user.getUid();
        currentReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentID).child("circle_members");
        loader.showloader();
        friendReference = FirebaseDatabase.getInstance().getReference().child("users").child(userID).child("circle_members");
        checker =new CircleMemberChecker(userID);
        checker.checkIfMember(userID, new CircleMemberChecker.CircleMemberCheckListener() {
                    @Override
                    public void onCheckComplete(boolean isMember) {
                        if (isMember) {
                            Toast.makeText(myactivity, "You are already friends", Toast.LENGTH_SHORT).show();
                        }else {
                            CircleJoin join = new CircleJoin(userID); // friend
                            CircleJoin join1 = new CircleJoin(user.getUid()); // my
                            //set my current id people's code
                            currentReference.child(userID).setValue(join);
                            //set friend id my code
                            friendReference.child(currentID).setValue(join1)
                                    .addOnCompleteListener(task -> {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(myactivity, "joined success", Toast.LENGTH_SHORT).show();
                                            loader.dismissloader();
                                        }
                                    });
                        }
                    }
                });
    }
    private void initializeViews() {
        loader = new ProgressbarLoader(myactivity);
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
    private String currentID;
    private String userID;
    private Activity myactivity;

    private DatabaseReference currentReference,friendReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressbarLoader loader;
    private CircleMemberChecker checker;
}

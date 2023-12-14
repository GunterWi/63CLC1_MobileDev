package com.nguyenquocthai.real_time_tracker;


import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nguyenquocthai.real_time_tracker.Model.Users;

import java.util.ArrayList;
import java.util.List;

public class ListFriend {
    public ListFriend(String current_uid) {
        this.current_uid = current_uid;
    }

    public void getListFriend(){
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        nameList= new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference("users");
        currentreference = FirebaseDatabase.getInstance().getReference("users").child(user.getUid()).child("circle_members");
        currentreference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameList.clear();

                if (dataSnapshot.exists())
                {
                    for (DataSnapshot dss: dataSnapshot.getChildren())
                    {
                        String circleid = dss.child("circleMemberId").getValue(String.class);

                        databaseReference.child(circleid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Users cuser = dataSnapshot.getValue(Users.class);
                                nameList.add(cuser);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    private FirebaseAuth auth;
    private FirebaseUser user;
    private List<Users> nameList;
    private String current_uid;
    private DatabaseReference databaseReference, currentreference;
}

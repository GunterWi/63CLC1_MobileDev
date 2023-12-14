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
    private List<Users> nameList;
    private String current_uid;
    private DatabaseReference databaseReference, currentreference;

    public interface DataStatus {
        void DataIsLoaded(List<Users> users);
    }

    public ListFriend(String current_uid) {
        this.current_uid = current_uid;
        this.nameList = new ArrayList<>();
        this.databaseReference = FirebaseDatabase.getInstance().getReference("users");
        this.currentreference = FirebaseDatabase.getInstance().getReference("users").child(current_uid).child("circle_members");
    }

    public void getListFriend(final DataStatus dataStatus) {
        currentreference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nameList.clear();
                final long childrenCount = dataSnapshot.getChildrenCount();

                if (childrenCount == 0) {
                    dataStatus.DataIsLoaded(nameList); // Call back immediately if there are no children
                }

                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    String circleid = dss.child("circleMemberId").getValue(String.class);
                    if (circleid != null) {
                        databaseReference.child(circleid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                Users cuser = dataSnapshot.getValue(Users.class);
                                if (cuser != null) {
                                    nameList.add(cuser);
                                }

                                // Check if all children have been processed
                                if (nameList.size() == childrenCount) {
                                    dataStatus.DataIsLoaded(new ArrayList<>(nameList));
                                }
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
}


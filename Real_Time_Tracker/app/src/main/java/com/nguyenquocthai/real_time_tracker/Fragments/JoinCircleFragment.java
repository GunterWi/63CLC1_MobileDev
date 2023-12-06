package com.nguyenquocthai.real_time_tracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

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
import com.nguyenquocthai.real_time_tracker.Model.CircleJoin;
import com.nguyenquocthai.real_time_tracker.Model.Users;
import com.nguyenquocthai.real_time_tracker.ProgressbarLoader;
import com.nguyenquocthai.real_time_tracker.R;


public class JoinCircleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_join_circle, container, false);
        initializeViews(view);
        currentID=user.getUid();
        currentReference = FirebaseDatabase.getInstance().getReference().child("users").child(currentID).child("circle_members");
        databaseReference= FirebaseDatabase.getInstance().getReference().child("users");
        joinbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joinbtnlistener();
            }
        });
        return view;
    }
    private void joinbtnlistener() {
        loader.showloader();
        Query query = databaseReference.orderByChild("circle_id").equalTo(pinview.getValue());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    //users userc;
                    for (DataSnapshot dss : snapshot.getChildren()) {
                            /*userc = dss.getValue(users.class);
                            joinuid = userc.getid();*/

                        String joinid = dss.child("id").getValue(String.class);
                        circleReference = FirebaseDatabase.getInstance().getReference().child("users").child(joinid).child("circle_members");

                        CircleJoin join = new CircleJoin(joinid);
                        CircleJoin join1 = new CircleJoin(user.getUid());
                        //set my current id people's code
                        currentReference.child(joinid).setValue(join);
                        //set friend id my code
                        circleReference.child(currentID).setValue(join1)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(getActivity(), "joined success", Toast.LENGTH_SHORT).show();
                                            loader.dismissloader();
                                        }
                                    }
                                });

                    }
                } else {
                    Toast.makeText(getActivity(), "this code is not available", Toast.LENGTH_SHORT).show();
                    loader.dismissloader();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void initializeViews(View view) {
        pinview= view.findViewById(R.id.pinview);
        joinbtn = view.findViewById(R.id.join_button);

        loader = new ProgressbarLoader(getActivity());
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
    }
    private String currentID;
    private Pinview pinview;
    private Button joinbtn;
    private DatabaseReference databaseReference, currentReference,circleReference;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private ProgressbarLoader loader;
}
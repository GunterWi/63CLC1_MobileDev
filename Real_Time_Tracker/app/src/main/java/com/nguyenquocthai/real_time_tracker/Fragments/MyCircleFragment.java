package com.nguyenquocthai.real_time_tracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenquocthai.real_time_tracker.Adapter.MembersAdapter;
import com.nguyenquocthai.real_time_tracker.Model.Users;
import com.nguyenquocthai.real_time_tracker.R;

import java.util.ArrayList;
import java.util.List;

public class MyCircleFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_circle, container, false);
        recyclerView= view.findViewById(R.id.myCircleRecyclerView);
        auth= FirebaseAuth.getInstance();
        user= auth.getCurrentUser();
        nameList= new ArrayList<>();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setHasFixedSize(true);
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
                                adapter.notifyDataSetChanged();
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
        adapter = new MembersAdapter(nameList, getActivity());
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        return view;

    }
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private List<Users> nameList;
    DatabaseReference databaseReference, currentreference;

}
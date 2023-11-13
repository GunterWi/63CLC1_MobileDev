package com.nguyenquocthai.real_time_tracker.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nguyenquocthai.real_time_tracker.R;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        initializeViews(view);
        initializeFirebase();
        setProfileDataListener();
        btnSave.setOnClickListener(v -> saveProfile());
        return view;
    }



    private void setProfileDataListener() {
        reference.child(current_uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("ProfileFragment", "Data changed");
                if (snapshot.exists()) {
                    String firstname = snapshot.child("firstname").getValue(String.class);
                    String lastname = snapshot.child("lastname").getValue(String.class);
                    String email = snapshot.child("email").getValue(String.class);
                    String password = snapshot.child("password").getValue(String.class);
                    firstnameEditText.setText(firstname);
                    lastnameEditText.setText(lastname);
                    emailEditText.setText(email);
                    passwordEditText.setText(password);
                    fullnameTextView.setText(lastname+" "+firstname);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("ProfileFragment", "Data loading cancelled", error.toException());
            }
        });
    }

    private void saveProfile() {
        Map<String, Object> update = new HashMap<>();
        update.put("firstname", firstnameEditText.getText().toString());
        update.put("lastname", lastnameEditText.getText().toString());
        update.put("email", emailEditText.getText().toString());
        update.put("password", passwordEditText.getText().toString());
        reference.child(current_uid).updateChildren(update).addOnCompleteListener(task -> {
            Toast.makeText(getActivity(), "Profile Updated", Toast.LENGTH_SHORT).show();
        });
    }
    private void initializeViews(View view) {
        fullnameTextView = view.findViewById(R.id.textview_fullname);
        firstnameEditText = view.findViewById(R.id.edittext_firstnameProfile);
        lastnameEditText = view.findViewById(R.id.edittext_lastnameProfile);
        emailEditText = view.findViewById(R.id.edittext_emailProfile);
        passwordEditText = view.findViewById(R.id.edittext_passwordProfile);
        btnSave = view.findViewById(R.id.save_button);
    }

    private void initializeFirebase() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        current_uid = user != null ? user.getUid() : "";
        reference = FirebaseDatabase.getInstance().getReference("users");
    }
    private TextView fullnameTextView;
    private EditText firstnameEditText, lastnameEditText, emailEditText, passwordEditText;
    private Button btnSave;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private static String current_uid;
}

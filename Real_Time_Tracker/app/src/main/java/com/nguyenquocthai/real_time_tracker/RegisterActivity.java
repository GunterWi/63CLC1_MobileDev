package com.nguyenquocthai.real_time_tracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.real_time_tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class RegisterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Initiation();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_LONG).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(txt_email, txt_password);
                }
            }
        });
        loginAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginAcitivity.class));
            }
        });
    }

    private void registerUser(String email, String password) {
        auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
            @Override
            public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                if (task.isSuccessful()) {
                    SignInMethodQueryResult result = task.getResult();
                    if (result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                        // Tài khoản đã tồn tại
                        Toast.makeText(RegisterActivity.this, "Account already exists!", Toast.LENGTH_LONG).show();
                    } else {
                        // Tài khoản chưa tồn tại, tiến hành đăng ký
                        auth.createUserWithEmailAndPassword(email, password)
                                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                            finish();
                                        }
                                    }
                                });
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Error checking account: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void Initiation() {
        email = findViewById(R.id.emailRegisV);
        password = findViewById(R.id.passRegisV);
        register = findViewById(R.id.submitRegisbtn);
        loginAcc = findViewById(R.id.alreadyAcc);
        auth = FirebaseAuth.getInstance();
    }

    private EditText email;
    private EditText password;
    private Button register;
    private FirebaseAuth auth;
    private TextView loginAcc;
}
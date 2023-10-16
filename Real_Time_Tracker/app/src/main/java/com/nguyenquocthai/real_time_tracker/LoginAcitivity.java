package com.nguyenquocthai.real_time_tracker;

import androidx.annotation.NonNull;
import  androidx.appcompat.app.AppCompatActivity;

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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginAcitivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Initiation();
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_email = email.getText().toString();
                String txt_password = password.getText().toString();
                loginUser(txt_email,txt_password);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginAcitivity.this,RegisterActivity.class));
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginAcitivity.this,"Login successfully!",Toast.LENGTH_LONG).show();
                startActivity(new Intent(LoginAcitivity.this,MainActivity.class));
                finish();
            }
        });
    }


    private void Initiation() {
        email = findViewById(R.id.emailLoginV);
        password = findViewById(R.id.passLoginV);
        login = findViewById(R.id.submitLoginbtn);
        createAccount= findViewById(R.id.dontAcc);
        auth = FirebaseAuth.getInstance();
    }

    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    private TextView createAccount;
}
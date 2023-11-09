package com.nguyenquocthai.real_time_tracker.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;
import com.nguyenquocthai.real_time_tracker.ProgressbarLoader;
import com.nguyenquocthai.real_time_tracker.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {


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
                loginUser(txt_email, txt_password);
            }
        });
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        if(auth.getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }

    private void loginUser(String email, String password) {
        loader.showloader();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(LoginActivity.this, "Email and password are required", Toast.LENGTH_LONG).show();
            loader.dismissloader();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            loader.dismissloader();
                            Toast.makeText(LoginActivity.this, "Login successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                            finish();
                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            loader.dismissloader();
                            String errorMessage = "Login failed: Invalid password";
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                errorMessage = "Login failed: User does not exist.";
                            }
                            Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        }
                    });
            loader.dismissloader();
        }

    }


    private void Initiation() {
        email = findViewById(R.id.edittext_email);
        password = findViewById(R.id.edittext_password);
        login = findViewById(R.id.login_button);
        createAccount = findViewById(R.id.logtosign);
        loader = new ProgressbarLoader(LoginActivity.this);
        auth = FirebaseAuth.getInstance();
    }

    private EditText email;
    private EditText password;
    private Button login;
    private FirebaseAuth auth;
    private TextView createAccount;
    private ProgressbarLoader loader;

}
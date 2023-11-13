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
        initializeViews();
        setupLoginButtonListener();
        setupCreateAccountTextViewListener();
        checkIfUserIsLoggedIn();
    }

    private void initializeViews() {
        emailEditText = findViewById(R.id.edittext_email);
        passwordEditText = findViewById(R.id.edittext_password);
        loginButton = findViewById(R.id.login_button);
        createAccountTextView = findViewById(R.id.logtosign);
        loader = new ProgressbarLoader(this);
        auth = FirebaseAuth.getInstance();
    }

    private void setupLoginButtonListener() {
        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            loginUser(email, password);
        });
    }

    private void setupCreateAccountTextViewListener() {
        createAccountTextView.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }

    private void checkIfUserIsLoggedIn() {
        if (auth.getCurrentUser() != null) {
            proceedToMainActivity();
        }
    }

    private void loginUser(String email, String password) {
        loader.showloader();
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Email and password are required", Toast.LENGTH_LONG).show();
        } else {
            auth.signInWithEmailAndPassword(email, password)
                    .addOnSuccessListener(authResult -> proceedToMainActivity())
                    .addOnFailureListener(e -> handleLoginFailure(e));
        }
        loader.dismissloader();
    }

    private void proceedToMainActivity() {
        loader.dismissloader();
        Toast.makeText(this, "Login successfully!", Toast.LENGTH_LONG).show();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

    private void handleLoginFailure(Exception e) {
        loader.dismissloader();
        String errorMessage = "Login failed: Invalid password";
        if (e instanceof FirebaseAuthInvalidCredentialsException) {
            errorMessage = "Login failed: User does not exist.";
        }
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show();
    }
    private EditText emailEditText, passwordEditText;
    private Button loginButton;
    private TextView createAccountTextView;
    private FirebaseAuth auth;
    private ProgressbarLoader loader;

}
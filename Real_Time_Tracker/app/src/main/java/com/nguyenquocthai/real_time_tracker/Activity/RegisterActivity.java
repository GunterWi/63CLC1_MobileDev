package com.nguyenquocthai.real_time_tracker.Activity;

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
                String txt_firstname = firstName.getText().toString();
                String txt_lastname = lastName.getText().toString();
                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)) {
                    Toast.makeText(RegisterActivity.this, "Empty credentials!", Toast.LENGTH_LONG).show();
                } else if (txt_password.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password too short!", Toast.LENGTH_LONG).show();
                } else {
                    registerUser(txt_email, txt_password,txt_firstname,txt_lastname);
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

    private void registerUser(String email, String password, String firstname, String lastname) {
        if (!isButtonClicked) {
            isButtonClicked = true; // Đánh dấu rằng button đã được click

            // Thực hiện công việc cần thiết ở đây
            auth.fetchSignInMethodsForEmail(email).addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                @Override
                public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                    try {
                        if (task.isSuccessful()) {
                            SignInMethodQueryResult result = task.getResult();
                            if (result.getSignInMethods() != null && result.getSignInMethods().size() > 0) {
                                // Tài khoản đã tồn tại
                                Toast.makeText(RegisterActivity.this, "Account already exists!", Toast.LENGTH_LONG).show();
                            } else {
                                Intent myIntent = new Intent(RegisterActivity.this, ConfirmActivity.class);
                                myIntent.putExtra("email", email);
                                myIntent.putExtra("password", password);
                                myIntent.putExtra("firstname", firstname);
                                myIntent.putExtra("lastname", lastname);
                                startActivity(myIntent);
                            }
                        } else {
                            // Xử lý lỗi nếu có
                            Toast.makeText(RegisterActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                            throw task.getException();
                        }
                    } catch (Exception e) {
                        // Xử lý trường hợp tắt mạng hoặc lỗi kết nối
                        Toast.makeText(RegisterActivity.this, "Network error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    } finally {
                        isButtonClicked = false;
                    }
                }
            });
        }
    }

    private void Initiation() {
        email = findViewById(R.id.edittext_signemail);
        password = findViewById(R.id.edittext_signpassword);
        firstName = findViewById(R.id.edittext_firstname);
        lastName = findViewById(R.id.edittext_lastname);
        register = findViewById(R.id.signup_button);
        loginAcc = findViewById(R.id.signtolog_txt);

        auth = FirebaseAuth.getInstance();
    }

    private EditText email;
    private EditText password;
    private EditText firstName;
    private EditText lastName;
    private Button register;
    private FirebaseAuth auth;
    private TextView loginAcc;
    private boolean isButtonClicked = false; // Biến cờ để kiểm tra đã click hay chưa

}
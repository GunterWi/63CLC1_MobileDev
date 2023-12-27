package com.nguyenquocthai.real_time_tracker.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Window;
import android.view.WindowManager;

import com.google.firebase.auth.FirebaseAuth;
import com.nguyenquocthai.real_time_tracker.ProgressbarLoader;
import com.nguyenquocthai.real_time_tracker.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar();

        setContentView(R.layout.activity_splash);
        initializeViews();
        loadUserProfileData();
    }

    private void loadUserProfileData() {
        if (auth.getCurrentUser() != null) {
            goToMainActivity();
        }
        else{
            goToLoginActivity();
        }
    }

    private void goToLoginActivity() {
        Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void goToMainActivity() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },  3000);
    }
    private void initializeViews() {
        auth = FirebaseAuth.getInstance();
    }
    private FirebaseAuth auth;

}

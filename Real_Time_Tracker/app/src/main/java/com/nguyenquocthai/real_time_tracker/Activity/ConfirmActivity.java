package com.nguyenquocthai.real_time_tracker.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nguyenquocthai.real_time_tracker.Model.Users;
import com.nguyenquocthai.real_time_tracker.ProgressbarLoader;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class ConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        Initiation();
        Intent myIntent = getIntent();
        if (myIntent != null) {
            email = myIntent.getStringExtra("email");
            readEmail.setText(email);
            password = myIntent.getStringExtra("password");
            readPassword.setText(password);
            firstname = myIntent.getStringExtra("firstname");
            readFirstName.setText(firstname);
            lastname = myIntent.getStringExtra("lastname");
            readLastName.setText(lastname);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i, 12);
            }
        });
        Code.setText(generateCode());
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signuplistner();
            }
        });
    }

    private void signuplistner() {
        loader.showloader();
        try{
            Date date = new Date();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault());
            final String strdate = dateFormat.format(date);
            if (resultUri != null) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            String userid = auth.getCurrentUser().getUid();
                            Users info = new Users(userid, readFirstName.getText().toString(), readLastName.getText().toString(), generateCode(), email, password, strdate, "null", 0, 0);
                            FirebaseDatabase.getInstance().getReference("users")
                                    .child(userid)
                                    .setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(ConfirmActivity.this, "submited..", Toast.LENGTH_SHORT).show();
                                            loader.dismissloader();
                                            Intent in = new Intent(ConfirmActivity.this, LoginActivity.class);
                                            startActivity(in);
                                            finish();
                                        }
                                    });

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(ConfirmActivity.this, "database response error", Toast.LENGTH_SHORT).show();
                            loader.dismissloader();
                        }
                    }
                });
            } else {
                Toast.makeText(ConfirmActivity.this, "Please choose an image", Toast.LENGTH_LONG).show();
                loader.dismissloader();
            }
        } catch (NullPointerException e) {
            Toast.makeText(ConfirmActivity.this, "error:" + e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 12 && resultCode == RESULT_OK && data != null) {
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
            Log.d("info", "đã chọn");
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                resultUri = result.getUri();
                circleImageView.setImageURI(resultUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }

    }

    private String generateCode() {
        Random r = new Random();
        int intcode = 100000 + r.nextInt(900000);
        String code = String.valueOf(intcode);
        return code;
    }

    private void Initiation() {
        readEmail = findViewById(R.id.editText_readmail);
        readPassword = findViewById(R.id.editText_readpassword);
        readFirstName = findViewById(R.id.editText_firstnamechange);
        readLastName = findViewById(R.id.editText_lastnamechange);
        confirm = findViewById(R.id.confirm_button);
        circleImageView = findViewById(R.id.circleImageView);
        Code = findViewById(R.id.txtcircle_id);
        auth = FirebaseAuth.getInstance();
        loader = new ProgressbarLoader(ConfirmActivity.this);
        reference = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    private EditText readEmail;
    private EditText readPassword;
    private EditText readFirstName;
    private EditText readLastName;
    private Button confirm;
    private TextView Code;
    private String email, password, firstname, lastname;
    private Uri resultUri;
    private CircleImageView circleImageView;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private ProgressbarLoader loader;
}
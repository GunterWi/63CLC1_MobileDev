package com.nguyenquocthai.real_time_tracker.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.real_time_tracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
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
        if(myIntent!=null){
            email=myIntent.getStringExtra("email");
            readEmail.setText(email);
            password=myIntent.getStringExtra("password");
            readPassword.setText(password);
            firstname=myIntent.getStringExtra("firstname");
            readFirstName.setText(firstname);
            lastname=myIntent.getStringExtra("lastname");
            readLastName.setText(lastname);
        }
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setAction(Intent.ACTION_GET_CONTENT);
                i.setType("image/*");
                startActivityForResult(i,12);
            }
        });
        confirmUser();
    }
    private void confirmUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ConfirmActivity.this, "Registered successfully!", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(ConfirmActivity.this,LoginAcitivity.class));
                            finish();
                        }
                    }
                });
    }
    private void confirmUser(){
        Date mydate = new Date();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.getDefault());
        String date = format1.format(mydate);
        Code.setText(generateCode());
        /*if(resultUri!=null){

        }
        else {
            Toast.makeText(ConfirmActivity.this,"Please choose an image",Toast.LENGTH_LONG).show();
        }*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==12 && resultCode==RESULT_OK&&data!=null){
            CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1,1)
                    .start(this);
            Log.d("info","đã chọn");
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
        circleImageView = findViewById(R.id.circleImageView);
        Code = findViewById(R.id.txtcircle_id);
//        register = findViewById(R.id.signup_button);
//        loginAcc = findViewById(R.id.signtolog_txt);
        auth = FirebaseAuth.getInstance();
    }
    private FirebaseAuth auth;
    private EditText readEmail;
    private EditText readPassword;
    private EditText readFirstName;
    private EditText readLastName;
    private TextView Code;
    private String email,password,firstname,lastname;
    private Uri resultUri;
    private CircleImageView circleImageView;
}
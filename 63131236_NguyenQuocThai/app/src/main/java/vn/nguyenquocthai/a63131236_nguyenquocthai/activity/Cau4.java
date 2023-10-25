package vn.nguyenquocthai.a63131236_nguyenquocthai.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import vn.nguyenquocthai.a63131236_nguyenquocthai.R;

public class Cau4 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cau4);
        Initiation();
        // Đặt sự kiện nghe khi ImageView được nhấn
        github.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://github.com/GunterWi";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));

                startActivity(intent);
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String websiteUrl = "https://www.facebook.com/daylacut1/";

                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl));

                startActivity(intent);
            }
        });
    }
    private void Initiation(){
        profileImage= findViewById(R.id.profile_image);
        github = findViewById(R.id.imageV_github);
        facebook = findViewById(R.id.imageV_facebook);

    }
    private ImageView profileImage;
    private ImageView github;
    private ImageView facebook;
}
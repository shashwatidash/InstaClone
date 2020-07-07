package com.satwick.instaclone;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.parse.ParseUser;

public class SocialMediaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_media);

        setTitle("Hi, " + ParseUser.getCurrentUser().getUsername() + "!");
    }
}

package com.satwick.instaclone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.List;

public class UserPosts extends AppCompatActivity {
    public static final String TAG = UserPosts.class.getSimpleName();

    private LinearLayout linearLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        //Log.d(TAG, "onCreate called");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users_tab);

        Intent receivedIntent = getIntent();
        Log.d(TAG, "iNTENT RECEIVED");
        final String receivedUsername = receivedIntent.getStringExtra("username");
        //Toast.makeText(this, receivedUsername, Toast.LENGTH_SHORT).show();
        Log.d(TAG, "iNTENT RECEIVED 2");
        setTitle("@" + receivedUsername + "'s Posts");
        linearLayout = findViewById(R.id.linearLayout);

        ParseQuery<ParseObject> parseQuery = new ParseQuery<ParseObject>("Photo");
        //key-value
        parseQuery.whereEqualTo("username", receivedUsername);
        parseQuery.orderByAscending("createdAt");

        parseQuery.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if (objects.size() > 0 && e == null) {
                    for(ParseObject post : objects){
                        final TextView postDesc = new TextView(UserPosts.this);
                        postDesc.setText(post.get("pictureDesc") + "");
                        final ParseFile postPicture = (ParseFile) post.get("picture");
                        postPicture.getDataInBackground(new GetDataCallback() {
                            @Override
                            public void done(byte[] data, ParseException e) {
                                if(data != null && e == null){
                                    Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0,data.length);
                                    ImageView postImageView = new ImageView(UserPosts.this);
                                    LinearLayout.LayoutParams imageParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    imageParams.setMargins(5,5,5,5);
                                    postImageView.setLayoutParams(imageParams);
                                    postImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                                    postImageView.setImageBitmap(bitmap);

                                    LinearLayout.LayoutParams descParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                                            ViewGroup.LayoutParams.WRAP_CONTENT);
                                    descParams.setMargins(5,5,5,15);
                                    postDesc.setLayoutParams(descParams);
                                    postDesc.setGravity(Gravity.CENTER);
                                    postDesc.setTextSize(20f);

                                    linearLayout.addView(postImageView);
                                    linearLayout.addView(postDesc);

                                }
                            }
                        });
                    }
                } else {
                    Toast.makeText(UserPosts.this, receivedUsername + " doesn't have any posts!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });

    }
}

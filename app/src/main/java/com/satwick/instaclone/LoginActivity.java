package com.satwick.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtPwd, edtUsername;
    private Button btnSignUp, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        setTitle("Login");

        edtUsername = findViewById(R.id.edtUsername);
        edtPwd = findViewById(R.id.edtPwd);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);


        btnSignUp.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
        if(ParseUser.getCurrentUser() != null) {
            ParseUser.logOut();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {

            case (R.id.btnSignUp):
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                break;

            case (R.id.btnSignIn):
                if(edtUsername.getText().toString().equals("") || edtPwd.getText().toString().equals("")){
                    Toast.makeText(LoginActivity.this, "Enter all credentials", Toast.LENGTH_SHORT).show();

                }else {
                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Logging In");
                    progressDialog.show();

                    ParseUser.logInInBackground(edtUsername.getText().toString(), edtPwd.getText().toString(), new LogInCallback() {
                        @Override
                        public void done(ParseUser user, ParseException e) {
                            if (user != null && e == null) {
                                Log.i("TAG", "msg");
                                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                                transitionToSocialMediaActivity();
                            } else {
                                Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                            progressDialog.dismiss();
                        }
                    });
                    break;
                }
        }
    }
    public void transitionToSocialMediaActivity(){
        Intent intent = new Intent(LoginActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}

package com.satwick.instaclone;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail, edtPwd, edtUsername;
    private Button btnSignUp, btnSignIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Sign Up");

        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPwd = findViewById(R.id.edtPwd);
        //enter key feature
        edtPwd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                //if enter key is pressed
                if( keyCode == KeyEvent.KEYCODE_ENTER && keyEvent.getAction() == KeyEvent.ACTION_DOWN){
                    onClick(btnSignUp);
                }
                return false;
            }
        });

        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn = findViewById(R.id.btnSignIn);

        /*
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

         */

        btnSignIn.setOnClickListener(this);
        btnSignUp.setOnClickListener(this);
        if(ParseUser.getCurrentUser() != null) {
           // ParseUser.logOut();
            transitionToSocialMediaActivity();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case (R.id.btnSignIn):
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            case (R.id.btnSignUp):

                if(edtEmail.getText().toString().equals("") || edtUsername.getText().toString().equals("") || edtPwd.getText().toString().equals("")){
                    //msg if all fields have not been entered
                    Toast.makeText(MainActivity.this, "Email, Username, Password is required*",Toast.LENGTH_SHORT).show();

                } else {
                    final ParseUser user = new ParseUser();

                    user.setEmail(edtEmail.getText().toString());
                    user.setUsername(edtUsername.getText().toString());
                    user.setPassword(edtUsername.getText().toString());

                    final ProgressDialog progressDialog = new ProgressDialog(this);
                    progressDialog.setMessage("Signing Up");
                    progressDialog.show();

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(MainActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                transitionToSocialMediaActivity();
                            } else {

                                Toast.makeText(MainActivity.this, "There was an error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        }
                    });

                }
                break;
        }
    }
    public void rootLayout(View view) {
        //remove keyboard from activity screen
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void transitionToSocialMediaActivity(){
        Intent intent = new Intent(MainActivity.this, SocialMediaActivity.class);
        startActivity(intent);
    }
}

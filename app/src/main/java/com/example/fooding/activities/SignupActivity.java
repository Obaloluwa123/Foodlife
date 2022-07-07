package com.example.fooding.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fooding.R;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

@SuppressWarnings("ALL")
public class SignupActivity extends AppCompatActivity {

    public static final String TAG = "SignUpActivity";

    private EditText etnewUsername;
    private EditText etnewPassword;
    private EditText etnewEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etnewUsername = findViewById(R.id.etnewUsername);
        etnewPassword = findViewById(R.id.etnewPassword);
        Button signupBtn = findViewById(R.id.signupBtn);
        etnewEmail = findViewById(R.id.etnewEmail);


        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = etnewEmail.getText().toString();
                String userName = etnewUsername.getText().toString();
                String password = etnewPassword.getText().toString();
                signUpUser(userName, password, email);

            }
        });
    }

    private void signUpUser(String userName, String password, String email) {
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.setEmail(email);
        user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Log.i(TAG, "onClick signUp button");
                                            Toast.makeText(getApplicationContext(), "Signed up sucessfully", Toast.LENGTH_SHORT);
                                            openLoginActivity();
                                        } else {
                                            Log.i(TAG, "onClick Error");
                                            Toast.makeText(getApplicationContext(), "Error Signing Up", Toast.LENGTH_SHORT);
                                            Log.e(TAG, e.toString());
                                        }
                                    }
                                }
        );
    }

    private void openLoginActivity() {
        Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

}
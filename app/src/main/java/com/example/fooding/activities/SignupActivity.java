package com.example.fooding.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
    private EditText etNewUsername;
    private EditText etNewPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        etNewUsername = findViewById(R.id.etnewUsername);
        etNewPassword = findViewById(R.id.etnewPassword);
        Button signupBtn = findViewById(R.id.signupBtn);
        etNewPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }

            }
        });

        signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = etNewUsername.getText().toString();
                String password = etNewPassword.getText().toString();
                signUpUser(userName, password);

            }
        });
    }

    private void signUpUser(String userName, String password) {
        ParseUser user = new ParseUser();
        user.setUsername(userName);
        user.setPassword(password);
        user.signUpInBackground(new SignUpCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e == null) {
                                            Toast.makeText(getApplicationContext(), "Signed up sucessfully", Toast.LENGTH_SHORT);
                                            openLoginActivity();
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Error Signing Up", Toast.LENGTH_SHORT);
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
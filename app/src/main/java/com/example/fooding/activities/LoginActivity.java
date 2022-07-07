package com.example.fooding.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fooding.R;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

@SuppressWarnings("ALL")
public class LoginActivity extends AppCompatActivity {

    public static final String TAG = "LoginActivity";
    ParseUser currentUser = ParseUser.getCurrentUser();
    private EditText etUsername;
    private EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etUsername = findViewById(R.id.etnewUsername);
        etPassword = findViewById(R.id.etnewPassword);
        Button etSignUp = findViewById(R.id.etSignUp);
        Button etLoginButton = findViewById(R.id.etLoginButton);

        etLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick login button");
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                loginUser(username, password);
                if (currentUser != null) {
                    openMainActivity();
                }
            }
        });

        etSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public void loginUser(String username, String password) {
        Log.i(TAG, "Attempting  to login user" + username);
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null) {
                    Log.e(TAG, "Issue with login", e);
                    return;
                }
                openMainActivity();
            }
        });
    }

    private void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
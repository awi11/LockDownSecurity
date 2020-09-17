package com.ucsd.lds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 *  This class is the first thing a user will see if he is not logged in
 *  MainActivity class will be responsible for checking if someone is logged in
 *  This class then will ask if a user wants to log in or signup
 *  Two Buttons will appear for users to choose.
 */

public class HomePage extends AppCompatActivity {

    private Button goToLogin;
    private Button goToSignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        // If user press login then it will be sent to login activity
        goToLogin = findViewById(R.id.goToLoginButton);
        goToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginPage = new Intent(HomePage.this,LogIn.class);
                startActivity(loginPage);
            }
        });

        goToSignup = findViewById(R.id.goToSignUpButton);
        goToSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signUpPage = new Intent(HomePage.this,SignUp.class);
                startActivity(signUpPage);
            }
        });
    }



}
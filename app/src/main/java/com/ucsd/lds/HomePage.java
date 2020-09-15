package com.ucsd.lds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
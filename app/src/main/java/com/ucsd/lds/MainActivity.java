package com.ucsd.lds;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth object
        mAuth = FirebaseAuth.getInstance();
    }

     /**
      * Checks if user is signed in (non-null) and update UI accordingly.
      * Get the current Firebase user and store it on currentUser
      * if currentUser is null then open the activity HomePage
      *
      *  Code from Tools->Firebase->Authentication-> (3)
      *  "Check current auth state"
      */
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            Intent homePageIntent = new Intent(MainActivity.this,HomePage.class);
            startActivity(homePageIntent);
            finish();
        }
    }

}
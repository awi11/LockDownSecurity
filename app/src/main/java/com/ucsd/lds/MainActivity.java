package com.ucsd.lds;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

//import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar mainToolbar;
    private Button messageBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Initialize Firebase Auth object
        mAuth = FirebaseAuth.getInstance();

        //set tool bar
        mainToolbar = findViewById(R.id.main_appbar_toolbar);
        setSupportActionBar(mainToolbar);
        getSupportActionBar().setTitle("LockDownSecurity");

        // set chat button
        messageBtn = (Button)findViewById(R.id.chatButton);
        messageBtn.setOnClickListener(view -> startActivity
                (new Intent(MainActivity.this, MessagingActivity.class)));
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
            sendToStart();
        }
    }

    private void sendToStart() {
            Intent homePageIntent = new Intent(MainActivity.this,HomePage.class);
            startActivity(homePageIntent);
            finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        super.onCreateOptionsMenu(menu);

        getMenuInflater().inflate(R.menu.main_menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.logout_button){
            FirebaseAuth.getInstance().signOut();
            sendToStart();
        }
        return true;
    }
}

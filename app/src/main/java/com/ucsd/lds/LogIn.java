package com.ucsd.lds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LogIn extends AppCompatActivity {

    // Fields
    private Toolbar mToolbar;
    private ProgressDialog mProgress;
    private EditText email_et;
    private EditText password_et;
    private Button login_button;

    // FirebaseAuth object
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //Firebase Auth set
        mAuth = FirebaseAuth.getInstance();

        // Toolbar set
        mToolbar = findViewById(R.id.login_app_bar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Log In");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Progress Dialog set
        mProgress = new ProgressDialog(this);

        //field set
        email_et = findViewById(R.id.et_email_login);
        password_et = findViewById(R.id.et_password_login);
        login_button = findViewById(R.id.but_login);

        /**
         * When the login button is clicked this method will collect the strings from the
         * edit text and send it to the method loginUser which is responsible to login using the
         * string fields.
         */
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email_address = email_et.getText().toString();
                String password = password_et.getText().toString();

                //Check if the strings are not empty if they are not then proceed
                if(!( TextUtils.isEmpty(email_address)|| TextUtils.isEmpty(password))){

                    /** mProgress are just responsible for the message that's going to show up
                     * while firebase is authenticating the fields and verifying it on the backend
                     * (This is the loading bar you see when you hit login)
                     */
                    mProgress.setTitle("Logging in User");
                    mProgress.setMessage("Please wait while");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    // call the loginUser method and pass in the required fields.
                   loginUser(email_address,password);
                }

            }
        });
    }

    /**
     *  This method will plug in the email and password from the edit text and try to call in a
     *  Firebase auth method called "signInWithEmailAndPassword" then it will process it and return
     *  a task which when successful will bring the user to the main menu.
     */
    private void loginUser(String email_address, String password) {

        mAuth.signInWithEmailAndPassword(email_address,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    mProgress.dismiss();
                    Intent mainActivity = new Intent(LogIn.this, MainActivity.class);
                    mainActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(mainActivity);
                    finish();

                } else {
                    mProgress.hide();
                    Toast.makeText(LogIn.this, "Unable to Log in", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}
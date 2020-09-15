package com.ucsd.lds;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {

    private EditText eUsername;
    private EditText eEmailAdd;
    private EditText ePassword;
    private Button signUpButt;

    // declare instance of firebase
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        eUsername = findViewById(R.id.et_name);
        eEmailAdd = findViewById(R.id.et_email);
        ePassword = findViewById(R.id.et_password);
        signUpButt = findViewById(R.id.signupbutts);

        signUpButt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String username = eUsername.getText().toString();
                String email_address = eEmailAdd.getText().toString();
                String password = ePassword.getText().toString();
                
                registerUser(username,email_address,password);





            }
        });
    }
    private void registerUser(String username, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Intent mainActivity = new Intent(SignUp.this, MainActivity.class);
                    startActivity(mainActivity);
                    finish();

                } else {
                    Toast.makeText(SignUp.this, "Got Problems", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
}
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

/**
 * This class is responsible for registering a new user
 * Three EditText will appear (name,email,password)
 * One Button will appear (SignUp button)
 */

public class SignUp extends AppCompatActivity {

    // declare the fields we need
    private EditText eUsername;
    private EditText eEmailAdd;
    private EditText ePassword;
    private Button signUpButt;

    // Declare a Firebase variable
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Now we assign an instance to the Firebase variable this will initialize FireBase Auth
        mAuth = FirebaseAuth.getInstance();

        // Assign each field with the corresponding field from the sign_up.xml
        eUsername = findViewById(R.id.et_name);
        eEmailAdd = findViewById(R.id.et_email);
        ePassword = findViewById(R.id.et_password);
        signUpButt = findViewById(R.id.signupbutts);

        /* When sign up button is click collect the input from the EditText and save it as
         * Strings. Then pass in the strings to the method "registerUser"
         */

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

    /**
     *
     * @param username
     * @param email
     * @param password
     *
     *  This method will call a method of the Firebase object mAuth "createUserWithEmail"
     *  Once this method finish there is going to be a Task<AuthResult> result
     *  this task will tell us if the signUp is successful or not
     *  If it is then you will be sent to the MainActivity which is our main menu
     *  If not a toast will appear ( temporarily)
     *
     *  If youre wondering where I got the code go to Tool-> Firebase-> Auth then got to number (5) still not sure why updateUI is not working
     *  Then watch this video starting from (17:00)
     *  https://www.youtube.com/watch?v=8VDCC26XGwY&list=PLGCjwl1RrtcQ3o2jmZtwu2wXEA4OIIq53&index=4&fbclid=IwAR2ypmIZyXLvY3CL2HegwPkiACJG53A78F29qjaXk7U90XtTTc4pGcqNz6I
     */
    private void registerUser(String username, String email, String password) {

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    // FirebaseUser user = mAuth.getCurrentUser();
                    //updateUI(user);
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
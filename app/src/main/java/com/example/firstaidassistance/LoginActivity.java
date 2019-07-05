package com.example.firstaidassistance;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private Button buttonSignIn;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private TextView textViewSignUp,textForgotPassword;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();


        progressDialog = new ProgressDialog(this);
        buttonSignIn =  findViewById(R.id.buttonSignIn);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextPassword =  findViewById(R.id.editTexPassword);
        textViewSignUp =  findViewById(R.id.textViewSignUp);
        textForgotPassword = findViewById(R.id.forgot_password);

        textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             startActivity(new Intent(LoginActivity.this,ResetPasswordActivity.class));
            }
        });

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        textViewSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                //will open register activity here
               startActivity(new Intent(LoginActivity.this,MainActivity.class));
            }
        });

    }
    private void userLogin(){

        String email = editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();

        if (TextUtils.isEmpty(email)){
            //email is empty
            editTextEmail.setError("Email is required");
            //Stopping the function from executing further
            editTextEmail.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();

        }

        if (TextUtils.isEmpty(password)){
            //password is empty
            editTextPassword.setError("Password is required");
            //stops the function from executing further
            editTextPassword.requestFocus();
            return;
        }
        //If validations are okay
        //we will start with showing the progress bar
        progressDialog.setMessage("Signing in,please wait...");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            //Start the navigationbar activity
                            finish();

                            Toast.makeText(getApplicationContext()," User Login Successful",Toast.LENGTH_SHORT).show();
                             startActivity(new Intent(getApplicationContext(),NavActivity.class));
                        }
                        else
                        {
                            progressDialog.dismiss();
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error:"+errorMessage,Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

    /*@Override
    protected void onStart() {
        super.onStart();
         if(firebaseAuth.getCurrentUser()!= null){
            // redirecting to Activityrecycler.
           // startActivity(new Intent(this,ActivityRecycler.class));
        }
    }*/
}

package com.example.firstaidassistance;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity  {
    private Button buttonRegister;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private TextView textViewSignin;
    private ProgressDialog progressDialog;

    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firebaseAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        progressDialog = new ProgressDialog(this);
        buttonRegister = (Button) findViewById(R.id.buttonRegister);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextPassword = (EditText) findViewById(R.id.editTexPassword);
        textViewSignin = (TextView) findViewById(R.id.textViewSignIn);




        //setup spinner



        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
        textViewSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });

    }


    private void registerUser(){

        final String name = editTextName.getText().toString().trim();
        final String email =editTextEmail.getText().toString().trim();
        String password =editTextPassword.getText().toString().trim();

        final String photoUrl = null ;



        //String imageUrl;

        /*'if(donor.isChecked()){

            donorState = "YES";
        }*/


        if(TextUtils.isEmpty(name)){
            //name is empty
            editTextName.setError("Field cannot be empty");
            //stopping execution
            editTextName.requestFocus();

        }


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
         if(password.length()<6){
            editTextPassword.setError("Minimum length of password should be 6");
            editTextPassword.requestFocus();

         }
        //If validations are okay
        //we will start with showing the progress bar
        progressDialog.setMessage("Registering User...");
        progressDialog.show();

        firebaseAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()){
                            //startActivity(new Intent(getApplicationContext(),ActivityRecycler.class));
                            //we will store the additional fields in firebase database

                      User user = new User(name,email,photoUrl);

                      FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                         .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {

                              if (task.isSuccessful()){
                                  finish();


                              }
                          }
                      });

                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"User Registered Successfully",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MainActivity.this,LoginActivity.class));
                        }
                        else {
                            progressDialog.dismiss();
                            String errorMessage = task.getException().getMessage();
                            Toast.makeText(getApplicationContext(),"Error:" + errorMessage,Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}

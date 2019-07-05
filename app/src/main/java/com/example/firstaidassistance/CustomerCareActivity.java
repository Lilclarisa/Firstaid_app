package com.example.firstaidassistance;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.Objects;

public class CustomerCareActivity extends AppCompatActivity {
    TextView supportText,emailCare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_care);

        Objects.requireNonNull(getSupportActionBar()).setTitle("First Aid Support"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action

        supportText = (TextView)findViewById(R.id.support);
        emailCare = (TextView)findViewById(R.id.care_email);

        emailCare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });
    }

    private void sendMail() {
        String recipientEmail = emailCare.getText().toString();
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL,recipientEmail);
        intent.setType("message/rfc822");
        startActivity(Intent.createChooser(intent,"choose an email application"));
    }

}

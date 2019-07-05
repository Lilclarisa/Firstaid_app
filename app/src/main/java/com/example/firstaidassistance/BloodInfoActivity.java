package com.example.firstaidassistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

public class BloodInfoActivity extends AppCompatActivity {
    private TextView info;
    private ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blood_info);

        image = (ImageView)findViewById(R.id.blood);
        info = (TextView)findViewById(R.id.info);


    }
}

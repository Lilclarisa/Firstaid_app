package com.example.firstaidassistance.PopUpKits;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;

import com.example.firstaidassistance.R;

import java.util.Objects;

public class AntibioticOitmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_antibiotic_oitment);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Antibiotic Ointment"); // for set actionbar title

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*0.8),(int)(height*0.6));

    }

}

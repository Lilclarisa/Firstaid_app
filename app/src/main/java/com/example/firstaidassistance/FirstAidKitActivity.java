package com.example.firstaidassistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.Objects;

public class FirstAidKitActivity extends AppCompatActivity {

    private RecyclerView kitRecyclerview;
    private int[]images = {R.drawable.adhesive_bandages,R.drawable.adhesive_tape,R.drawable.antibiotic_oitment,
            R.drawable.blanket,R.drawable.calamine_lotion,R.drawable.coldpack,R.drawable.elastic_bandage,R.drawable.flashlight,
            R.drawable.gauze,R.drawable.glove,R.drawable.medications,R.drawable.mouthpiece_cpr,R.drawable.safetypin,R.drawable.sharp_scissors,
            R.drawable.soap,R.drawable.thermometer,R.drawable.tweezers,R.drawable.wipes};
    private String [] imageTitle ={"Adhesive Bandages","Adhesive Tape","Antibiotic Ointment","Blanket","Calamine Lotion","Cold pack","Elastic Bandage","Flashlight","Gauze",
                                   "Glove","Medications","Mouthpiece CPR","Safety pins","Sharp scissors","Soap","Thermometer","Tweezers","Wipes"};
    private KitRecyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_aid_kit);

        Objects.requireNonNull(getSupportActionBar()).setTitle("First aid Kit Contents"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action

        kitRecyclerview = findViewById(R.id.kit_recyclerview);
        layoutManager = new GridLayoutManager(this,2);
        kitRecyclerview.setHasFixedSize(true);
        kitRecyclerview.setLayoutManager(layoutManager);
        adapter = new KitRecyclerAdapter(this,images,imageTitle);
        kitRecyclerview.setAdapter(adapter);
    }
}

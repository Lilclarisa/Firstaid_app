package com.example.firstaidassistance;

import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ContactActivity extends AppCompatActivity {

   private RecyclerView recyclerView;
   private String[]list ={"Ambulance","Police","Fire","Police Hotline","Non-Emergency Ambulance"};
   private String[]contact = {"997","999","998","1800 255 0000","1777"};
   private ContactAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Emergency Contacts"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action

        recyclerView = (RecyclerView)findViewById(R.id.recyclerContact);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter = new ContactAdapter(this, list,contact);
        recyclerView.setAdapter(adapter);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        int id = item.getItemId();
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

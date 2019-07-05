package com.example.firstaidassistance;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class NavActivity extends AppCompatActivity{
    protected DrawerLayout drawer;

    FirebaseAuth mAuth;
    FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();



        drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_accident:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new AccidentsFragment()).commit();
                        break;
                    case R.id.nav_log_out:
                        logout();
                        break;
                    case R.id.nav_kit:
                        Intent intent = new Intent(getApplicationContext(), FirstAidKitActivity.class);
                        startActivity(intent);
                        break;

                    case R.id.nav_near_me:
                        intent = new Intent(getApplicationContext(), MapsActivity.class);
                        startActivity(intent);
                        break;
                    case R.id.nav_call:
                        intent = new Intent(getApplicationContext(), ContactActivity.class);
                        startActivity(intent);
                        break;

                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new AccidentsFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_accident);
        }
        updateNavHeader();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.care){
            Intent intent = new Intent(getApplicationContext(), CustomerCareActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.help){
            String google = "https://secure-sands-22220.herokuapp.com/";
            Uri web = Uri.parse(google);

            Intent gotogoogle = new Intent(Intent.ACTION_VIEW, web);
            if (gotogoogle.resolveActivity(getPackageManager()) != null) {

                startActivity(gotogoogle);
            }



        }



        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else {
            super.onBackPressed();
        }
    }
    private void logout(){

        FirebaseAuth.getInstance().signOut();

        Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    public void updateNavHeader(){
       FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReference().child("profilepics/").child(currentUser.getUid()+"png");

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView userEmail = headerView.findViewById(R.id.user_email);
        ImageView profileImage = headerView.findViewById(R.id.profile_image);

        //storageReference = storage.getReference().child("profilepics/").child(currentUser.getUid());

       if (currentUser != null){

           if(currentUser.getEmail() != null) {
               userEmail.setText(currentUser.getEmail());
           }
           if(currentUser.getPhotoUrl() != null) {
               //we use glide to load user image
               Glide.with(this)
                       .load(currentUser.getPhotoUrl().toString())//currentUser.getPhotoUrl().toString()
                       .circleCrop()
                       .into(profileImage);
           }

        }

    }


}

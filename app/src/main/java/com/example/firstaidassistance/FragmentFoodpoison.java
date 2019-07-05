package com.example.firstaidassistance;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class FragmentFoodpoison  extends AppCompatActivity {
   // private String imageUrl;
    //private String imageName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.foodpoison_fragment);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Food Poisoning"); // for set actionbar title
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // for add back arrow in action

        getIncomingIntent();

        String guide = (String) getString(R.string.image_description);
       // guide.setText(HTML.fromHtml(getString(R.id.stringWithStyleTags)));

    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra("image_url")&& getIntent().hasExtra("image_name")){

        String imageUrl = getIntent().getStringExtra("image_url");
        String imageName = getIntent().getStringExtra("image_name");

        setImage(imageUrl,imageName);

        }
    }

    private void setImage(String imageUrl, String imageName) {
        TextView name = findViewById(R.id.image_description);
        name.setText(imageName);

        ImageView image = findViewById(R.id.image);
        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .into(image);
    }
}

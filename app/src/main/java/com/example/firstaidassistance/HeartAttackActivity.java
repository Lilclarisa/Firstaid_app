package com.example.firstaidassistance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Objects;

public class HeartAttackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heart_attack);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Heart Attack"); // for set actionbar title
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

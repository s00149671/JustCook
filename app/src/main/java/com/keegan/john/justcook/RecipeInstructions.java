package com.keegan.john.justcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.keegan.john.justcook.MainPage.EXTRA_HEAD;
import static com.keegan.john.justcook.MainPage.EXTRA_IMAGE;
import static com.keegan.john.justcook.MainPage.EXTRA_Ingred;

public class RecipeInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_instructions);

        Intent intent = getIntent();
        String head = intent.getStringExtra(EXTRA_HEAD);
        String image = intent.getStringExtra(EXTRA_IMAGE);
        String ingred = intent.getStringExtra(EXTRA_Ingred);

        ImageView imageView = findViewById(R.id.imageDetail);
        TextView textView = findViewById(R.id.imageDescription);
        TextView textView1 = findViewById(R.id.imageIngredients);

        Picasso.with(this).load(image).fit().centerInside().into(imageView);
        textView.setText(head);
        textView1.setText(ingred);


    }


}


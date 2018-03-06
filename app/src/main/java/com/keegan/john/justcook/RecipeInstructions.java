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

        import android.support.v7.app.AppCompatActivity;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageView;

        import static com.keegan.john.justcook.R.layout.activity_recipe_instructions;

public class RecipeInstructions extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        Intent intent = getIntent();
        String head = intent.getStringExtra(EXTRA_HEAD);
        String image = intent.getStringExtra(EXTRA_IMAGE);
        String ingred = intent.getStringExtra(EXTRA_Ingred);

        ImageView imageView = findViewById(R.id.RecipeImage);
        TextView textView = findViewById(R.id.editText);
        TextView textView1 = findViewById(R.id.editText4);

        Picasso.with(this).load(image).fit().centerInside().into(imageView);
        textView.setText(head);
        textView1.setText(ingred);


    }


}


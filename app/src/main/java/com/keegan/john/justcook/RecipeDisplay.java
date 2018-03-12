package com.keegan.john.justcook;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by emily on 06/03/2018.
 */

public class RecipeDisplay extends AppCompatActivity{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipe_display);

        //intent
        Intent intent = getIntent();


        //creating variables for the passed data and adding the data to them
        String recipeName = intent.getStringExtra("name");
        String recipeDescription = intent.getStringExtra("description");


        //creating variables for the checkboxes I passed through
        Boolean checkboxVegetarian = getIntent().getExtras().getBoolean("checkboxVegetarian");
        Boolean checkboxVegan = getIntent().getExtras().getBoolean("checkboxVegan");
        Boolean checkboxCeliac = getIntent().getExtras().getBoolean("checkboxCeliac");
        Boolean checkboxPescetarian = getIntent().getExtras().getBoolean("checkboxPescetarian");
        Boolean checkboxNutFree = getIntent().getExtras().getBoolean("checkboxNutFree");
        Boolean checkboxDairyFree = getIntent().getExtras().getBoolean("checkboxDairyFree");


        //creating variables for the ingredients passed through intent from the activity newRecipePage
        ArrayList<String> Ingredients_List = new ArrayList<String>();
        Ingredients_List = intent.getStringArrayListExtra("Ingredient_List");
        //assiging to a text view




        //receiving image
        ImageView imageBox;
        imageBox = (ImageView)findViewById(R.id.imageBox);

        Bundle extras = getIntent().getExtras();
        Bitmap bmp = (Bitmap) extras.getParcelable("imagebitmap");

        imageBox.setImageBitmap(bmp);

        // ImageView.setImageBitmap(bmp );

        //assigning the name, description and ingredients views to a variable
        TextView nameView=(TextView) findViewById(R.id.nameView);
        nameView.setText(recipeName);
        TextView descriptionView=(TextView) findViewById(R.id.descriptionView);
        descriptionView.setText(recipeDescription);
        TextView ingredientsView=(TextView) findViewById(R.id.ingredientsView);
        for (int i=0; i<Ingredients_List.size();i++){
            ingredientsView.append(Ingredients_List.get(i));
            ingredientsView.append("\n");
        }
        //setContentView(LinearLayoutView);


        // ingredientsView.setText(Arrays.toString(Ingredients_List).replaceAll("\\[|\\]", ""));
      //  ingredientsView.set(Ingredients_List);

        //assigning checkboxes individually
        TextView vegetarianTxt =(TextView) findViewById(R.id.vegetarianText);
        TextView veganTxt = (TextView) findViewById(R.id.veganText);
        TextView celiacTxt = (TextView) findViewById(R.id.celiacText);
        TextView pescetarianTxt = (TextView) findViewById(R.id.pescetarianText);
        TextView nutTxt = (TextView) findViewById(R.id.nutfreeText);
        TextView dairyTxt = (TextView) findViewById(R.id.dairyfreeText);



        //inputing correct values for checkboxes
        if(checkboxVegetarian) {
            vegetarianTxt.setText("Yes");
        }
        else vegetarianTxt.setText(("No"));
        if(checkboxVegan) {
            veganTxt.setText("Yes");
        }
        else veganTxt.setText(("No"));
        if(checkboxCeliac) {
            celiacTxt.setText("Yes");
        }
        else celiacTxt.setText(("No"));
        if(checkboxPescetarian) {
            pescetarianTxt.setText("Yes");
        }
        else pescetarianTxt.setText(("No"));
        if(checkboxNutFree) {
            nutTxt.setText("Yes");
        }
        else nutTxt.setText(("No"));
        if(checkboxDairyFree) {
            dairyTxt.setText("Yes");

        }
        else dairyTxt.setText(("No"));




    }

}

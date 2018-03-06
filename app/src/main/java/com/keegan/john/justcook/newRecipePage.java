package com.keegan.john.justcook;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import android.content.Intent;
import android.support.annotation.MainThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import android.support.design.widget.FloatingActionButton;
//import android.support.design.widget.Snackbar;

import android.app.Activity;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

/**
 * Created by emily on 06/03/2018.
 */

public class newRecipePage extends AppCompatActivity {

    private CheckBox checkboxVegetarian, checkboxVegan, checkboxCeliac, checkboxPescetarian, checkboxNutFree, checkboxDairyFree;
    private EditText recipeName, recipeDescription;
    private FloatingActionButton saveRecipeButton;
    private Button imageButton;

    ImageView ivImage;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;

    //intent variables
    public static final String EXTRA_NAME = "EXTRA_NAME";
    private static final int REQUEST_RESPONSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipepage_new);
        addListenerOnButton();

        imageButton = (Button) findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelectImage();
            }
        });



    }

    private void addListenerOnButton() {

        //assigning the checkboxes to variables
        checkboxVegetarian = (CheckBox) findViewById(R.id.checkBox1_vegetarian);
        checkboxVegan = (CheckBox) findViewById(R.id.checkBox2_vegan);
        checkboxCeliac = (CheckBox) findViewById(R.id.checkBox3_celiac);
        checkboxPescetarian = (CheckBox) findViewById(R.id.checkBox4_pescetarian);
        checkboxNutFree = (CheckBox) findViewById(R.id.checkBox5_NutFree);
        checkboxDairyFree = (CheckBox) findViewById(R.id.checkBox5_DairyFree);
        //assigning the EditText (text boxes) to variables
        recipeName = (EditText) findViewById(R.id.recipeName);
        recipeDescription = (EditText) findViewById(R.id.recipeDescription);
        //assigning main create recipe button to variable
        saveRecipeButton = (FloatingActionButton) findViewById(R.id.saveButton);
        //image
        ivImage = (ImageView)findViewById(R.id.ivImage);


        saveRecipeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //adding the checkboxes to the result array
                StringBuffer result = new StringBuffer();
                result.append(checkboxVegetarian.isChecked());
                result.append(checkboxVegan.isChecked());
                result.append(checkboxCeliac.isChecked());
                result.append(checkboxPescetarian.isChecked());
                result.append(checkboxNutFree.isChecked());
                result.append(checkboxDairyFree.isChecked());

                //String checkedCheckboxes = new String();

                //Hashtable<String,Boolean> example = new Hashtable<>();
                //example.put("vegetarian", checkboxVegetarian.isChecked());
                //  example.get
                //example.get("vegetarian");


                //adding the values of edit text to string variables
                //this will allow it to be pushed between activities
                String EXTRA_NAME = recipeName.getText().toString();
                String EXTRA_DESC = recipeDescription.getText().toString();



                //creating the intent object and adding variables
                Intent myIntent = new Intent(newRecipePage.this, RecipeDisplay.class);
                myIntent.putExtra("name", EXTRA_NAME);
                myIntent.putExtra("description", EXTRA_DESC);


                //sending over each checkbox individually
                myIntent.putExtra("checkboxVegetarian", checkboxVegetarian.isChecked());
                myIntent.putExtra("checkboxVegan", checkboxVegan.isChecked());
                myIntent.putExtra("checkboxCeliac", checkboxCeliac.isChecked());
                myIntent.putExtra("checkboxPescetarian", checkboxPescetarian.isChecked());
                myIntent.putExtra("checkboxNutFree", checkboxNutFree.isChecked());
                myIntent.putExtra("checkboxDairyFree", checkboxDairyFree.isChecked());


                // myIntent.putExtra("checkedCheckboxes", checkedCheckboxes);

                //passing the image
                ivImage.buildDrawingCache();
                Bitmap image= ivImage.getDrawingCache();

                Bundle imageBundle = new Bundle();
                imageBundle.putParcelable("imagebitmap", image);
                myIntent.putExtras(imageBundle);


                startActivity(myIntent);
            }

        });
    }

    private void SelectImage(){

        final CharSequence[] items={"Camera","Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(newRecipePage.this);

        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (items[i].equals("Camera")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);

                } else if (items[i].equals("Gallery")) {

                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    // startActivityForResult(intent.createChooser(intent, "Select File"), SELECT_FILE);
                    startActivityForResult(intent, SELECT_FILE);

                } else if (items[i].equals("Cancel")) {
                    dialogInterface.dismiss();
                }
            }
        });
        builder.show();

    }
    @Override
    public  void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode,data);

        if(resultCode== Activity.RESULT_OK){

            if(requestCode==REQUEST_CAMERA){

                Bundle bundle = data.getExtras();
                final Bitmap bmp = (Bitmap) bundle.get("data");
                ivImage.setImageBitmap(bmp);

            }else if(requestCode==SELECT_FILE){

                Uri selectedImageUri = data.getData();
                ivImage.setImageURI(selectedImageUri);
            }

        }



    }
}

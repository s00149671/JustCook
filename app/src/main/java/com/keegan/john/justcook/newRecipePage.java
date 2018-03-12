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
import android.widget.TextView;
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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by emily on 06/03/2018.
 */

public class newRecipePage extends AppCompatActivity {

    private CheckBox checkboxVegetarian, checkboxVegan, checkboxCeliac, checkboxPescetarian, checkboxNutFree, checkboxDairyFree;
    private EditText recipeName, recipeDescription;
    private FloatingActionButton saveRecipeButton;
    private Button imageButton, selectIngredients;

    DatabaseReference databaseReference;

    ImageView ivImage;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;

    //alert dialog arrays
    ArrayList<String> Ingredients_All = new ArrayList<String>();
    TextView mIngredientsSelected;
    String[] Vegetables_Items;
    String[] Meats_Items;
    String[] Fish_Items;
    boolean[] checkedIngredients_Meats;
    boolean[] checkedIngredients_Fish;
    boolean[] checkedIngredients_Veg;
    ArrayList<Integer> mUserItems_Meats = new ArrayList<>();
    ArrayList<Integer> mUserItems_Fish = new ArrayList<>();
    ArrayList<Integer> mUserItems_Veg = new ArrayList<>();

    //intent variables
    public static final String EXTRA_NAME = "EXTRA_NAME";
    private static final int REQUEST_RESPONSE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipepage_new);
        addListenerOnButton();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Recipes");

        //passing the check box values for vegetables
        Vegetables_Items = getResources().getStringArray(R.array.Vegitables);
        checkedIngredients_Veg = new boolean[Vegetables_Items.length];

        //passing the check box values for Meats
        Meats_Items = getResources().getStringArray(R.array.Meats);
        checkedIngredients_Meats = new boolean[Meats_Items.length];

        //passing the check box values for Fish
        Fish_Items = getResources().getStringArray(R.array.Fish);
        checkedIngredients_Fish = new boolean[Fish_Items.length];

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
        selectIngredients =(Button) findViewById(R.id.ingredientsbutton);
        //image
        ivImage = (ImageView)findViewById(R.id.ivImage);
        //ingredients text view
        mIngredientsSelected = (TextView) findViewById(R.id.ingredientsListView);

        selectIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final CharSequence[] items = {"Meat", "Fish", "Vegetables", "Cancel"};

                AlertDialog.Builder builder = new AlertDialog.Builder(newRecipePage.this);

                builder.setTitle("Add Ingredients");

                builder.setItems(items, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (items[i].equals("Meat")) {
                            AlertDialog.Builder mBuilder = new AlertDialog.Builder(newRecipePage.this);
                            mBuilder.setTitle("Meats");
                            mBuilder.setMultiChoiceItems(Meats_Items, checkedIngredients_Meats, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                    if(isChecked){
                                        if(!mUserItems_Meats.contains(position))
                                        {
                                            mUserItems_Meats.add(position);
                                        }
                                        else if (mUserItems_Meats.contains(position)){
                                            mUserItems_Meats.remove(position);
                                        }
                                    }
                                }
                            });
                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String item = "";
                                    for(int i = 0; i < mUserItems_Meats.size(); i++)
                                    {
                                        item = item + Meats_Items[mUserItems_Meats.get(i)];
                                        //check to see if it is the last item. add "," if its not the last item
                                        if(i != mUserItems_Meats.size() -1 );
                                        {
                                            item = item + ", ";
                                        }

                                    }
                                    mIngredientsSelected.setText(item);
                                    Ingredients_All.add(item);
                                }
                            });
                            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            //clear btn
                            mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    for(int i = 0; i< checkedIngredients_Meats.length; i++)
                                    {
                                        checkedIngredients_Meats[i] = false;
                                        mUserItems_Meats.clear();
                                        mIngredientsSelected.setText("");

                                    }
                                }
                            });
                            AlertDialog mDialog = mBuilder.create();
                            mDialog.show();



                        } else if (items[i].equals("Fish")) {
                            android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(newRecipePage.this);
                            mBuilder.setTitle("Fish");
                            mBuilder.setMultiChoiceItems(Fish_Items, checkedIngredients_Fish, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                    if(isChecked){
                                        if(!mUserItems_Fish.contains(position))
                                        {
                                            mUserItems_Fish.add(position);
                                        }
                                        else if (mUserItems_Fish.contains(position)){
                                            mUserItems_Fish.remove(position);
                                        }
                                    }
                                }
                            });
                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String item = "";
                                    for(int i = 0; i < mUserItems_Fish.size(); i++)
                                    {
                                        item = item + Fish_Items[mUserItems_Fish.get(i)];
                                        //check to see if it is the last item. add "," if its not the last item
                                        if(i != mUserItems_Fish.size() -1);
                                        {
                                            item = item + ",";
                                        }
                                    }
                                    mIngredientsSelected.setText(item);
                                    Ingredients_All.add(item);
                                }
                            });
                            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            //clear btn
                            mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    for(int i = 0; i< checkedIngredients_Fish.length; i++)
                                    {
                                        checkedIngredients_Fish[i] = false;
                                        mUserItems_Fish.clear();
                                        mIngredientsSelected.setText("");
                                    }
                                }
                            });
                            android.app.AlertDialog mDialog = mBuilder.create();
                            mDialog.show();


                        } else if (items[i].equals("Vegetables")) {
                           android.app.AlertDialog.Builder mBuilder = new android.app.AlertDialog.Builder(newRecipePage.this);
                            mBuilder.setTitle("Vegetables");
                            mBuilder.setMultiChoiceItems(Vegetables_Items, checkedIngredients_Veg, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                                    if(isChecked){
                                        if(!mUserItems_Veg.contains(position))
                                        {
                                            mUserItems_Veg.add(position);
                                        }
                                        else if (mUserItems_Veg.contains(position)){
                                            mUserItems_Veg.remove(position);
                                        }
                                    }
                                }
                            });
                            mBuilder.setCancelable(false);
                            mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    String item = "";
                                    for(int i = 0; i < mUserItems_Veg.size(); i++)
                                    {
                                        item = item + Vegetables_Items[mUserItems_Veg.get(i)];
                                        //check to see if it is the last item. add "," if its not the last item
                                        if(i != mUserItems_Veg.size() -1);
                                        {
                                            item = item + ",";
                                        }

                                    }
                                    mIngredientsSelected.setText(item);
                                    Ingredients_All.add(item);


                                }
                            });
                            mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                }
                            });
                            //clear btn
                            mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int which) {
                                    for(int i = 0; i< checkedIngredients_Veg.length; i++)
                                    {
                                        checkedIngredients_Veg[i] = false;
                                        mUserItems_Veg.clear();
                                        mIngredientsSelected.setText("");
                                    }
                                }
                            });
                            android.app.AlertDialog mDialog = mBuilder.create();
                            mDialog.show();


                        } else if (items[i].equals("Cancel")) {
                            dialogInterface.dismiss();
                        }
                    }
                });
                builder.show();


            }

        });



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


                //adding the values of edit text to string variables
                //this will allow it to be pushed between activities
                String EXTRA_NAME = recipeName.getText().toString();
                String EXTRA_DESC = recipeDescription.getText().toString();

                //passing the variables for the new recipe into the NewRecipe Class
                /* Create recipe object using constructor */
                NewRecipe recipenew = new NewRecipe(EXTRA_NAME);

                // Invoking methods for each object created
                recipenew.recipeDescription(EXTRA_DESC);
                recipenew.recipeIngredientsList(Ingredients_All);
                recipenew.checkboxes(checkboxVegetarian.isChecked(),checkboxVegan.isChecked(),
                        checkboxCeliac.isChecked(),checkboxPescetarian.isChecked(),
                        checkboxNutFree.isChecked(),checkboxDairyFree.isChecked());






                //creating the intent object and adding variables
                Intent myIntent = new Intent(newRecipePage.this, RecipeDisplay.class);
                myIntent.putExtra("name", EXTRA_NAME);
                myIntent.putExtra("description", EXTRA_DESC);


                //sending over each checkbox individually
                myIntent.putExtra("checkboxVegetarian", checkboxVegetarian.isChecked());
                myIntent.putExtra("checkboxVegan", checkboxVegan.isChecked());
               // myIntent.putExtra("checkboxCeliac", checkboxCeliac.isChecked());
                myIntent.putExtra("checkboxPescetarian", checkboxPescetarian.isChecked());
                myIntent.putExtra("checkboxNutFree", checkboxNutFree.isChecked());
                myIntent.putExtra("checkboxDairyFree", checkboxDairyFree.isChecked());


                // myIntent.putExtra("checkedCheckboxes", checkedCheckboxes);
                myIntent.putExtra("ingredients", Ingredients_All );

                //passing the image
                ivImage.buildDrawingCache();
                Bitmap image= ivImage.getDrawingCache();

                Bundle imageBundle = new Bundle();
                imageBundle.putParcelable("imagebitmap", image);
                myIntent.putExtras(imageBundle);

                //Saving the new recipe to database using firebase (method below)
         //       AddData(Ingredients_All);

                //starting next activity
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

    public void AddData(ArrayList Ingredients_All){

        String Name = recipeName.getText().toString().trim();
        String Description = recipeDescription.getText().toString().trim();
        Boolean Vegetarian = checkboxVegetarian.isChecked();
        Boolean Vegan =  checkboxVegan.isChecked();
       //Boolean Celiac = checkboxCeliac.isChecked();
        Boolean Pescetarian = checkboxPescetarian.isChecked();
        Boolean nutFree = checkboxNutFree.isChecked();
        Boolean dairyFree = checkboxDairyFree.isChecked();
        ArrayList<String> ingredients_List =  Ingredients_All;

        SaveNewRecipeData savedata = new SaveNewRecipeData(Name, Description,
                ingredients_List,  Vegetarian,
                Vegan, Pescetarian,nutFree, dairyFree);

       // databaseReference.setValue(savedata);


    }

}
 class NewRecipe {

     String Name;
     String Description;
     Boolean Vegan;
     Boolean Vegetarian;
     Boolean Pescetarian;
     Boolean Celiac;
     Boolean NutFree;
     Boolean DairyFree;
     ArrayList<String> ingredients_list = new ArrayList<String>();




     // This is the constructor of the class NewRecipe
     public NewRecipe(String name) {
        name = Name;
     }



     /* Assign the recipe description to the variable designation.*/
     public void recipeDescription(String description) {
         description = Description;
     }

     /* Assign the image to the variable*/
     public void recipeImage(String image) {

         //allow for passing of image *********
     }

     public void checkboxes(Boolean vegetarian, Boolean vegan, Boolean celiac, Boolean pesscetarian, Boolean nutfree, Boolean dairyfree){

         vegetarian = Vegetarian;
         vegan = Vegan;
         celiac = Celiac;
         pesscetarian = Pescetarian;
         nutfree = NutFree;
         dairyfree = DairyFree;
     }


     /* Assign the ingredients list array to the variable*/
     public void recipeIngredientsList(ArrayList<String> Ingredient_List) {

         ingredients_list = Ingredient_List;
         //allow for passing of Ingredient_List list array *********
     }



 }

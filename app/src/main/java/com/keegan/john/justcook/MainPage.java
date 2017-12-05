package com.keegan.john.justcook;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;


import java.util.ArrayList;

/**
 * Created by Fidel Rose on 21/11/2017.
 */

public class MainPage extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    //multiple choice list dialog
    Button mVeg;
    TextView mIngredientsSelected;
    String[] ingredientsItems;
    boolean[] checkedIngredients;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    //Floating Image Button
    ImageButton floatingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        //Floating Image Button
        floatingButton = (ImageButton) findViewById(R.id.floatBtn);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Float Btn Works",Toast.LENGTH_SHORT).show();
            }
        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //multiple choice list dialog
        mVeg = (Button) findViewById(R.id.btnVeg);
        mIngredientsSelected = (TextView) findViewById(R.id.tvVeg);

        //passing the value
        ingredientsItems = getResources().getStringArray(R.array.ingredients_items);
        checkedIngredients = new boolean[ingredientsItems.length];

        //when you click on the buton it will show the dialog
        mVeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
                mBuilder.setTitle("Vegetables");
                mBuilder.setMultiChoiceItems(ingredientsItems, checkedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
                        if(isChecked){
                            if(!mUserItems.contains(position))
                            {
                                mUserItems.add(position);
                            }
                            else if (mUserItems.contains(position)){
                                mUserItems.remove(position);
                            }
                        }
                    }
                });
                mBuilder.setCancelable(false);
                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        String item = "";
                        for(int i = 0; i < mUserItems.size(); i++)
                        {
                            item = item + ingredientsItems[mUserItems.get(i)];
                            //check to see if it is the last item. add "," if its not the last item
                            if(i != mUserItems.size() -1);
                            {
                                item = item + ",";
                            }
                        }
                        mIngredientsSelected.setText(item);
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
                        for(int i = 0; i< checkedIngredients.length; i++)
                        {
                            checkedIngredients[i] = false;
                            mUserItems.clear();
                            mIngredientsSelected.setText("");

                        }
                    }
                });

                AlertDialog mDialog = mBuilder.create();
                mDialog.show();

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        //Options Selected Nagigation

        switch (item.getItemId())
        {
            case R.id.profile:
                //profile
                Intent profile = new Intent(this,Profile.class);
                startActivity(profile);
                break;
            case R.id.settings:
                //settings
                Intent settings = new Intent(this,SettingsActivity.class);
                startActivity(settings);
                break;
            case R.id.signout:
                //Signout
                Intent signout = new Intent(this,MainActivity.class);
                startActivity(signout);
                Toast.makeText(getApplicationContext(), "Signed Out",Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_Vegetables:
                //Veg List
                Toast.makeText(getApplicationContext(), "Vegitables",Toast.LENGTH_SHORT).show();
                break;


            default:
                //unkown error
        }
        return super.onOptionsItemSelected(item);

    }

    //options Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.options_menu,menu);

        return super.onCreateOptionsMenu(menu);


    }

//



    //Side Nav Button Control
//    @SuppressWarnings("StatementWithEmptyBody")
//    public  boolean onNavigationItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        FragmentManager fragmentManager = getFragmentManager();
//
//        if (id == R.id.nav_Meat){
//            Toast.makeText(MainPage.this, "Welcome", Toast.LENGTH_LONG).show();
//        }
//
//        return true;
//
//    }
}

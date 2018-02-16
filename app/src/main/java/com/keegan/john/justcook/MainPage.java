package com.keegan.john.justcook;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import  android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;



import java.util.ArrayList;

/**
 * Created by Fidel Rose on 21/11/2017.
 */

public class MainPage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;


    //GRIDVIEW TEST
//    GridView gridView;
//
//    String txtList[] = {"Lasagne","Thai Red Curry"};
//
//    int foodPic[] = {R.drawable.lasagne, R.drawable.thai};
    //multiple choice list dialog

    TextView mIngredientsSelected;
    String[] Vegetables_Items;
    String[] Meats_Items;
    String[] Fish_Items;
    boolean[] checkedIngredients_Meats;
    boolean[] checkedIngredients_Fish;
    boolean[] checkedIngredients_Veg;
    ArrayList<Integer> mUserItems = new ArrayList<>();

    //Floating Image Button
    ImageButton floatingButton;

    //navigation over action bar
    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);

        //Nav Drawer
        NavigationView navigationView =(NavigationView)findViewById(R.id.Navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Nav Bar Over Action Bar
        mToolbar =(Toolbar) findViewById(R.id.nav_action);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //GRIDVIEW TEST
//        gridView = (GridView) findViewById(R.id.gridView);
//        GridTrial gridTrial = new GridTrial(MainPage.this,foodPic,txtList);
//        gridView.setAdapter(gridTrial);




        ImageButton lasagneBtn = (ImageButton) findViewById(R.id.lasagneBtn);
        ImageButton thaiBtn = (ImageButton) findViewById(R.id.thaiBtn);

        lasagneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipe1 = new Intent(MainPage.this, RecipeInstructions.class);
                startActivity(recipe1);
            }
        });
        lasagneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipe2 = new Intent(MainPage.this, RecipeInstructions.class);
                startActivity(recipe2);
            }
        });

        //Floating Image Button
        floatingButton = (ImageButton) findViewById(R.id.floatBtn);
        floatingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),
                        "Float Btn Works",Toast.LENGTH_SHORT).show();
            }
        });




        //Ingrediance choicen is being displayed
        mIngredientsSelected = (TextView) findViewById(R.id.tvVeg);

        //passing the check box values for vegitables
        Vegetables_Items = getResources().getStringArray(R.array.Vegitables);
        checkedIngredients_Veg = new boolean[Vegetables_Items.length];

        //passing the check box values for Meats
        Meats_Items = getResources().getStringArray(R.array.Meats);
        checkedIngredients_Meats = new boolean[Meats_Items.length];

        //passing the check box values for Fish
        Fish_Items = getResources().getStringArray(R.array.Fish);
        checkedIngredients_Fish = new boolean[Fish_Items.length];


    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }

        //Options Selected Nagigation
//        switch (item.getItemId())
//        {
//
//            case R.id.nav_Vegetables:
//                //Veg List
//                Toast.makeText(getApplicationContext(), "Vegetables",Toast.LENGTH_SHORT).show();
//                break;
//
//
//            default:
//                //unkown error
//        }


        return super.onOptionsItemSelected(item);

    }

    //options Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        getMenuInflater().inflate(R.menu.navigation_menu,menu);


        return super.onCreateOptionsMenu(menu);


    }





    //Side Nav Button Control
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public  boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_Meat) {

            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
            mBuilder.setTitle("Meats");
            mBuilder.setMultiChoiceItems(Meats_Items, checkedIngredients_Meats, new DialogInterface.OnMultiChoiceClickListener() {
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
                        item = item + Meats_Items[mUserItems.get(i)];
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
                    for(int i = 0; i< checkedIngredients_Meats.length; i++)
                    {
                        checkedIngredients_Meats[i] = false;
                        mUserItems.clear();
                        mIngredientsSelected.setText("");

                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }
        if (id == R.id.nav_Fish){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
            mBuilder.setTitle("Fish");
            mBuilder.setMultiChoiceItems(Fish_Items, checkedIngredients_Fish, new DialogInterface.OnMultiChoiceClickListener() {
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
                        item = item + Fish_Items[mUserItems.get(i)];
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
                    for(int i = 0; i< checkedIngredients_Fish.length; i++)
                    {
                        checkedIngredients_Fish[i] = false;
                        mUserItems.clear();
                        mIngredientsSelected.setText("");

                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }

        if (id == R.id.nav_Vegetables){
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
            mBuilder.setTitle("Vegetables");
            mBuilder.setMultiChoiceItems(Vegetables_Items, checkedIngredients_Veg, new DialogInterface.OnMultiChoiceClickListener() {
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
                        item = item + Vegetables_Items[mUserItems.get(i)];
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
                    for(int i = 0; i< checkedIngredients_Veg.length; i++)
                    {
                        checkedIngredients_Veg[i] = false;
                        mUserItems.clear();
                        mIngredientsSelected.setText("");

                    }
                }
            });

            AlertDialog mDialog = mBuilder.create();
            mDialog.show();

        }





        return true;

    }

}

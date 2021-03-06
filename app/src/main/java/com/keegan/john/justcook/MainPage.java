package com.keegan.john.justcook;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Paint;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import  android.support.v7.widget.Toolbar;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageButton;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rapidapi.rapidconnect.RapidApiConnect;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
//
/**
 * Created by Fidel Rose on 21/11/2017.
 */
public class MainPage extends AppCompatActivity implements MyAdapter.OnItemClickListener, NavigationView.OnNavigationItemSelectedListener {
    public static final String EXTRA_HEAD = "recipeHead";
    public static final String EXTRA_IMAGE = "ImageUrl";
    public static final String EXTRA_Ingred = "ingred";
    public static EditText txtSearch;
    public static final String URL_DATA = "http://api.yummly.com/v1/api/recipes?_app_id=a7c56f9f&_app_key=4b001e89379d681015faf52129230ce9&q=%s";
    public static final String URL_Ingrediance = "http://api.yummly.com/v1/api/recipes?_app_id=a7c56f9f&_app_key=4b001e89379d681015faf52129230ce9&q=%s";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private List<ListItem> listItems;
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
    ImageButton floatingButton;
    private Toolbar mToolbar;
    Button IngredianceSearch;
    Button SearchButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainpage);
        txtSearch = (EditText) findViewById(R.id.txtSearch);
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
        //RecyclerView
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        final String url = String.format(URL_DATA, "");
        loadRecyclerViewData(url);


       floatingButton = (ImageButton) findViewById(R.id.floatBtn);
      floatingButton.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent myIntent = new Intent(MainPage.this, newRecipePage.class);
               startActivity(myIntent);
          }
        });
        IngredianceSearch = (Button) findViewById(R.id.IngredianceSearch);
        IngredianceSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String[] meatNames = new String[mUserItems_Meats.size()];
                for(int i = 0; i < meatNames.length; i++){
                    meatNames[i] = Meats_Items[mUserItems_Meats.get(i)];
                }
                String[] vegNames = new String[mUserItems_Veg.size()];
                for(int i = 0; i < vegNames.length; i++){
                    vegNames[i] = Vegetables_Items[mUserItems_Veg.get(i)];
                }
                String[] fishNames = new String[mUserItems_Fish.size()];
                for(int i = 0; i < fishNames.length; i++){
                    fishNames[i] = Fish_Items[mUserItems_Fish.get(i)];
                }


                String MeatFormat = TextUtils.join("&allowedIngredient[]=",meatNames);
               String VegFormat = TextUtils.join("&allowedIngredient[]=", vegNames);
                String FishFormat = TextUtils.join("&allowedIngredient[]=",fishNames);
                //String formatTest = String.format("%s",VegFormat);

                String AllFormats = String.format("%s%s%s",MeatFormat,FishFormat,VegFormat);

                final String UrlFormat = String.format(URL_Ingrediance, AllFormats);
                loadRecyclerViewData(UrlFormat);
            }
        });


            SearchButton = (Button) findViewById(R.id.Search);
            SearchButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     final String Url = String.format(URL_DATA, txtSearch.getText().toString());
                     loadRecyclerViewData(Url);
                }
            });


        //Ingrediance choicen is being displayed
        mIngredientsSelected = (TextView) findViewById(R.id.tvVeg);

        //passing the check box values for vegetables
        Vegetables_Items = getResources().getStringArray(R.array.Vegitables);
        checkedIngredients_Veg = new boolean[Vegetables_Items.length];
        //passing the check box values for Meats
        Meats_Items = getResources().getStringArray(R.array.Meats);
        checkedIngredients_Meats = new boolean[Meats_Items.length];
        //passing the check box values for Fish
        Fish_Items = getResources().getStringArray(R.array.Fish);
        checkedIngredients_Fish = new boolean[Fish_Items.length];
    }

    //LOAD API
    private void loadRecyclerViewData(String loadUrl){
       listItems.clear();
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                loadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {

                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray array = jsonObject.getJSONArray("matches");
                            for (int i = 0; i<array.length(); i++) {
                                JSONObject o = array.getJSONObject(i);
                                JSONArray smallImageUrls = o.getJSONArray("smallImageUrls");
                               // for (int k = 0; k < smallImageUrls.length(); k++) {

                                    String smallImageUrlValue = smallImageUrls.get(0).toString();

                                    JSONArray ingredients = o.getJSONArray("ingredients");
                                    // for (int j = 0; j < ingredients.length(); j++) {
                                    String ingredientValue = ingredients.get(0).toString();
//                                    String smallImageUrlValue = smallImageUrls.get(0).toString();
                                    ListItem item = new ListItem(
                                            o.getString("recipeName"),
                                            smallImageUrlValue,
                                            ingredientValue

                                    );
                                    listItems.add(item);
                                }


                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);
                            ((MyAdapter)adapter).setOnItemClickListener(MainPage.this);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            AlertDialog.Builder alert = new AlertDialog.Builder(MainPage.this);
                            alert.setTitle("Error");
                            alert.setMessage(e.getMessage());
                            alert.setPositiveButton("OK", null);
                            alert.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), volleyError.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.nav_Profile)
        {
            Intent intentProfile = new Intent(MainPage.this,Profile.class);
            startActivity(intentProfile);
            return  true;
        }
        if(id == R.id.nav_Settings)
        {
            Intent intentProfile = new Intent(MainPage.this,SettingsActivity.class);
            startActivity(intentProfile);
            return  true;
        }
        if(id == R.id.nav_Logout)
        {
            Intent intentProfile = new Intent(MainPage.this,MainActivity.class);
            startActivity(intentProfile);
            return  true;
        }
        if (mToggle.onOptionsItemSelected(item))
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //options Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.optionmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
    //Side Nav Button Control
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public  boolean onNavigationItemSelected(MenuItem item) {
//        InputMethodManager inputMethodManager = (InputMethodManager)  this.getSystemService(Activity.INPUT_METHOD_SERVICE);
//        inputMethodManager.hideSoftInputFromWindow(this.getCurrentFocus().getWindowToken(), 0);
        int id = item.getItemId();
            if (id == R.id.nav_Meat) {
            AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
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
                        if(i != mUserItems_Meats.size() -1);
                        {
                            item = item + "";
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
                        mUserItems_Meats.clear();
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
                            item = item + "";
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
                        mUserItems_Fish.clear();
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
                            item = item + "";
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
                        mUserItems_Veg.clear();
                        mIngredientsSelected.setText("");
                    }
                }
            });
            AlertDialog mDialog = mBuilder.create();
            mDialog.show();
        }
        return true;
    }
    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, RecipeInstructions.class);
        ListItem clickedItem = listItems.get(position);

        detailIntent.putExtra(EXTRA_HEAD, clickedItem.getHead());
        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getImageUrl());
        detailIntent.putExtra(EXTRA_Ingred, clickedItem.getIngred());
        startActivity(detailIntent);
    }

}

package com.keegan.john.justcook;

import android.app.AlertDialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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
//
/**
 * Created by Fidel Rose on 21/11/2017.
 */
public class MainPage extends AppCompatActivity implements MyAdapter.OnItemClickListener{

    public static final String EXTRA_HEAD = "recipeHead";
    public static final String EXTRA_IMAGE = "ImageUrl";
    public static final String EXTRA_Ingred = "ingred";
    public static EditText txtSearch;
    private static final String URL_DATA = "http://api.yummly.com/v1/api/recipes?_app_id=a7c56f9f&_app_key=4b001e89379d681015faf52129230ce9&q=burger";
    //private static final String URL_D = "http://api.yummly.com/v1/api/recipes?_app_id=a7c56f9f&_app_key=4b001e89379d681015faf52129230ce9&q="+txtSearch.getText.toString()+"&allowedIngredient[]="+ itemChecked.getText.toString();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private List<ListItem> listItems;

   // TextView recipeTitle;
   // ImageView recipePic;
    //ListView listView;


    //GRIDVIEW TEST
//    GridView gridView;
//
//    String txtList[] = {"Lasagne","Thai Red Curry"};
//
//    int foodPic[] = {R.drawable.lasagne, R.drawable.thai};
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

        txtSearch = (EditText) findViewById(R.id.txtSearch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        listItems = new ArrayList<>();
        loadRecyclerViewData();





        //recipeTitle = (TextView)findViewById(R.id.recipeTitle);
        //recipePic = (ImageView)findViewById(R.id.recipePic);
        //listView = (ListView) findViewById(R.id.listView);

//        DownloadTask task = new DownloadTask();
//        task.execute("http://api.yummly.com/v1/api/recipes?_app_id=a7c56f9f&_app_key=4b001e89379d681015faf52129230ce9&requirePictures=true");

        //Floating Image Button
//        floatingButton = (ImageButton) findViewById(R.id.floatBtn);
//        floatingButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(getApplicationContext(),
//                        "Float Btn Works",Toast.LENGTH_SHORT).show();
//            }
//        });


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout,R.string.open,R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //multiple choice list dialog
//        mVeg = (Button) findViewById(R.id.btnVeg);
//        mIngredientsSelected = (TextView) findViewById(R.id.tvVeg);

        //passing the value
//        ingredientsItems = getResources().getStringArray(R.array.ingredients_items);
//        checkedIngredients = new boolean[ingredientsItems.length];
//
//        //when you click on the buton it will show the dialog
//        mVeg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(MainPage.this);
//                mBuilder.setTitle("Vegetables");
//                mBuilder.setMultiChoiceItems(ingredientsItems, checkedIngredients, new DialogInterface.OnMultiChoiceClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int position, boolean isChecked) {
//                        if(isChecked){
//                            if(!mUserItems.contains(position))
//                            {
//                                mUserItems.add(position);
//                            }
//                            else if (mUserItems.contains(position)){
//                                mUserItems.remove(position);
//                            }
//                        }
//                    }
//                });
//                mBuilder.setCancelable(false);
//                mBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        String item = "";
//                        for(int i = 0; i < mUserItems.size(); i++)
//                        {
//                            item = item + ingredientsItems[mUserItems.get(i)];
//                            //check to see if it is the last item. add "," if its not the last item
//                            if(i != mUserItems.size() -1);
//                            {
//                                item = item + ",";
//                            }
//                        }
//                        mIngredientsSelected.setText(item);
//                    }
//                });
//                mBuilder.setNegativeButton("Dismiss", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        dialogInterface.dismiss();
//                    }
//                });
//                //clear btn
//                mBuilder.setNeutralButton("Clear all", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int which) {
//                        for(int i = 0; i< checkedIngredients.length; i++)
//                        {
//                            checkedIngredients[i] = false;
//                            mUserItems.clear();
//                            mIngredientsSelected.setText("");
//
//                        }
//                    }
//                });
//
//                AlertDialog mDialog = mBuilder.create();
//                mDialog.show();
//
//            }
//        });


    }


private void loadRecyclerViewData(){
    final ProgressDialog progressDialog = new ProgressDialog(this);
    progressDialog.setMessage("Loading Data...");
    progressDialog.show();

    StringRequest stringRequest = new StringRequest(Request.Method.GET,
            URL_DATA,
            new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    progressDialog.dismiss();
                    try {

                        JSONObject jsonObject = new JSONObject(response);
                        JSONArray array = jsonObject.getJSONArray("matches");
                        for (int i = 0; i<array.length(); i++){
                            JSONObject o = array.getJSONObject(i);

                            //JSONObject imageUrlsBySize = o.getJSONObject("imageUrlsBySize");
                            //String ninty = imageUrlsBySize.getString("90");
                            JSONArray smallImageUrls = o.getJSONArray("smallImageUrls");
                            String smallImageUrlValue = smallImageUrls.get(0).toString();

                            JSONArray ingredients = o.getJSONArray("ingredients");
                            String ingredientValue = ingredients.get(0).toString();
                            //JSONArray clivesMadeUpKey = o.optJSONArray("clivesMadeUpKey"); // returns null
                            //JSONArray clivesMadeUpKey2 = o.getJSONArray("clivesMadeUpKey"); //throws exception

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
                        //adapter.setOnItemClickListener(MainPage.this);
                    } catch (JSONException e) {
                        e.printStackTrace();
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
//    public class DownloadTask extends AsyncTask<String,Void,String> {
//
//        @Override
//        protected String doInBackground(String... urls) {
//
//            String result = "";
//            URL url;
//            HttpURLConnection urlConnection = null;
//            try {
//                url = new URL (urls[0]);
//                urlConnection = (HttpURLConnection) url.openConnection();
//                InputStream in = urlConnection.getInputStream();
//                InputStreamReader reader = new InputStreamReader(in);
//                int data = reader.read();
//                while (data != -1) {
//                    char current = (char) data;
//                    result += current;
//                    data = reader.read();
//                }
//                return result;
//            } catch (MalformedURLException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//        @Override
//        protected void onPostExecute(String result) {
//            super.onPostExecute(result);
//            try {
//                String main = "";
//
//                JSONObject jsonObject = new JSONObject(result);
//               String recipe = jsonObject.getString("matches");
//                Log.i("Website Content", recipe);
//
//                JSONArray arr = new JSONArray(recipe);
//                for (int i = 0; i < arr.length(); i++) {
//                    JSONObject jsonPart = arr.getJSONObject(i);
//                    Log.i("recipeName", jsonPart.getString("recipeName"));
//                    Log.i("imageUrlsBySize", jsonPart.getString("imageUrlsBySize"));
//                    main = jsonPart.getString("recipeName");
//                }
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
      //  }
   // }

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
                Toast.makeText(getApplicationContext(), "Vegetables",Toast.LENGTH_SHORT).show();
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





    //Side Nav Button Control
    @SuppressWarnings("StatementWithEmptyBody")
    public  boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

        if (id == R.id.nav_Meat){
            Toast.makeText(MainPage.this, "Welcome", Toast.LENGTH_LONG).show();
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

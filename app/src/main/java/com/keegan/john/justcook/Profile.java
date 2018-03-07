package com.keegan.john.justcook;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;


/**
 * Created by Fidel Rose on 20/11/2017.
 */

public class Profile extends AppCompatActivity {

    String UserName;
    FirebaseDatabase database;
    DatabaseReference users;
    TextView editUsername, editPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);

        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        editUsername = (TextView) findViewById(R.id.UsersName);



    }
}

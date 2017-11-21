package com.keegan.john.justcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.SaveCallback;

public class MainActivity extends AppCompatActivity {

    public void clickFunctionSubmit(View view) {

        EditText usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        Log.i("Username", usernameTxt.getText().toString());

        EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        Log.i("Password", passwordTxt.getText().toString());
        Toast.makeText(MainActivity.this, "Welcome" + usernameTxt.getText().toString() , Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Parse.enableLocalDatastore(this);

        Parse.initialize(new Parse.Configuration.Builder(getApplicationContext())
                .applicationId("")
                .clientKey("")
                .server("") //trailing slash
                .build());

        ParseObject User = new ParseObject("User");
        User.put("username", "Fidel");
        User.put("password", "12345");
        User.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {

                if (e == null) {
                    Log.i("SaveInBackGround", "Successful");
                } else {
                    Log.i("SaveInBackGround", "Failed.error" + e.toString());
                }
            }
        });
        ParseAnalytics.trackAppOpenedInBackground(getIntent());


        setContentView(R.layout.activity_main);

        Button forgotButton=(Button)findViewById(R.id.forgotButton);
        Button createButton=(Button)findViewById(R.id.createButton);

        forgotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgot = new Intent(MainActivity.this, ForgotPassword.class);
                startActivity(forgot);
            }
        });

        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent create = new Intent(MainActivity.this, CreateAccount.class);
                startActivity(create);
            }
        });
    }
}

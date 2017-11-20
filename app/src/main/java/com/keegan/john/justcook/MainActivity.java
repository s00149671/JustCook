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

public class MainActivity extends AppCompatActivity {
private Button button;

    public void clickFunctionSubmit(View view) {

        EditText usernameTxt = (EditText) findViewById(R.id.usernameTxt);
        Log.i("Username", usernameTxt.getText().toString());

        EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);
        Log.i("Password", passwordTxt.getText().toString());
        Toast.makeText(MainActivity.this, "Welcome" + usernameTxt.getText().toString() , Toast.LENGTH_LONG).show();
    }

    //public void clickFunctionForget(View view) {

        //Intent forgot = new Intent(MainActivity.this, ForgotPassword.class);
       // startActivity(forgot);
    //}
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


       // Add your initialization code here



    button = (Button) findViewById(R.id.forgotButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openForgotPassword();
            }
        });
    }

    public void openForgotPassword() {
        Intent forgot = new Intent(MainActivity.this, ForgotPassword.class);
        startActivity(forgot);
    }
}

package com.keegan.john.justcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.keegan.john.justcook.Model.User;

public class MainActivity extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    EditText editUsername, editPassword;
    Button submitButton;

//    public void clickFunctionSubmit(View view) {
//
//        EditText usernameTxt = (EditText) findViewById(R.id.usernameTxt);
//        Log.i("Username", usernameTxt.getText().toString());
//
//        EditText passwordTxt = (EditText) findViewById(R.id.passwordTxt);
//        Log.i("Password", passwordTxt.getText().toString());
//        Toast.makeText(MainActivity.this, "Welcome" +" "+ usernameTxt.getText().toString() , Toast.LENGTH_LONG).show();
//    }

    @Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button forgotButton = (Button) findViewById(R.id.forgotButton);
        Button createButton = (Button) findViewById(R.id.createButton);

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

//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent submit = new Intent(MainActivity.this, MainPage.class);
//                startActivity(submit);
//            }
//        });
        //Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        submitButton = (Button) findViewById(R.id.submitbutton);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(editUsername.getText().toString(),
                        editPassword.getText().toString());
            }
        });

                                        }

    private void signIn(final String username, final String password) {
        users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.child(username).exists()){
                    if(!username.isEmpty()) {
                        User login = dataSnapshot.child(username).getValue(User.class);
                        if(login.getPassword().equals(password)) {
                            Toast.makeText(MainActivity.this,"Login Successful", Toast.LENGTH_SHORT).show();
                            Intent submit = new Intent(MainActivity.this, MainPage.class);
                            startActivity(submit);
                        }
                        else {
                            Toast.makeText(MainActivity.this, "Incorrect Login Details", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Incorrect Details", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}

package com.keegan.john.justcook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class CreateAccount extends AppCompatActivity {

    //Firebase
    FirebaseDatabase database;
    DatabaseReference users;

    EditText editUsername, editPassword, editEmail;
    Button btnCreateAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        //Firebase
        database = FirebaseDatabase.getInstance();
        users = database.getReference("Users");

        editUsername = (EditText) findViewById(R.id.editUsername);
        editPassword = (EditText) findViewById(R.id.editPassword);
        editEmail = (EditText) findViewById(R.id.editEmail);
        btnCreateAcc = (Button) findViewById(R.id.btnCreateAcc);

        btnCreateAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final User user = new User(editUsername.getText().toString(),
                        editPassword.getText().toString(),
                        editEmail.getText().toString());
               Intent account = new Intent(CreateAccount.this, MainActivity.class);
               startActivity(account);

                users.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.child(user.getUsername()).exists())
                            Toast.makeText(CreateAccount.this,"This Username Already Exists",Toast.LENGTH_SHORT).show();
                        else {
                            users.child(user.getUsername()).setValue(user);
                            Toast.makeText(CreateAccount.this,"Account Created Successfully",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });
    }
}

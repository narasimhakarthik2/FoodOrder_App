package com.k.menu;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.k.menu.common.common;
import com.k.menu.model.User;

public class Sign_up extends AppCompatActivity {
    EditText name,phoneNo,password;
    Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        phoneNo = (EditText) findViewById(R.id.phoneNo);
        name = (EditText) findViewById(R.id.name);
        password = (EditText) findViewById(R.id.password);

        btnSignUp = (Button) findViewById(R.id.btnSignUp);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(Sign_up.this);
                mDialog.setMessage("please wait");
                mDialog.show();

                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.child(phoneNo.getText().toString()).exists())
                        {
                            mDialog.dismiss();
                            Toast.makeText(Sign_up.this,"Phone Number already Registered",Toast.LENGTH_SHORT).show();
                        }
                    else
                        {
                            mDialog.dismiss();
                            User user = new User(name.getText().toString(),password.getText().toString(),phoneNo.getText().toString());
                            table_user.child(phoneNo.getText().toString()).setValue(user);
                            Toast.makeText(Sign_up.this,"Sign Up Successfull!!!",Toast.LENGTH_SHORT).show();
                            Intent navi = new Intent(Sign_up.this, navi.class);
                            common.currentuser=user;
                            startActivity(navi);
                            finish();
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

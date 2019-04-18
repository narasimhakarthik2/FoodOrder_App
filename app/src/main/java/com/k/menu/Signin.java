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
import com.k.menu.model.User;
import com.k.menu.common.common;

public class Signin extends AppCompatActivity {
     EditText phoneNo,password;
     Button btnSignin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        phoneNo = (EditText) findViewById(R.id.phoneNo);
        password = (EditText) findViewById(R.id.password);
        btnSignin = (Button) findViewById(R.id.sign_in_button);

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final ProgressDialog mDialog = new ProgressDialog(Signin.this);
                mDialog.setMessage("please wait");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        if (dataSnapshot.child(phoneNo.getText().toString()).exists()){
                            mDialog.dismiss();
                        User user = dataSnapshot.child(phoneNo.getText().toString()).getValue(User.class);
                        user.setPhone(phoneNo.getText().toString());
                        if (user.getPassword().equals(password.getText().toString())) {
                            Intent navi = new Intent(Signin.this, navi.class);
                            common.currentuser=user;
                            startActivity(navi);
                            finish();
                        } else {
                            Toast.makeText(Signin.this, "Sign_in failed", Toast.LENGTH_SHORT).show();
                              }
                        }
                        else
                        {
                            mDialog.dismiss();
                            Toast.makeText(Signin.this, " User not exist", Toast.LENGTH_SHORT).show();
                        }
                }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed(){
        Intent int1 = new Intent(this,MainActivity.class) ;
        startActivity(int1);
        finish();

    }
}

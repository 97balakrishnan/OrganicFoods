package com.example.rajesh.organicfoods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button Login,Signup,admin;
    Intent intent;
    String uname,pwd;
    EditText username,password;
    FirebaseAuth auth;
    ProgressDialog progress;
    DatabaseReference db;



    public void checkLoggedin()
    {
        auth=FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null) {
            finish();
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
        }
    }

    public void checkAdmin(String u,String p)
    {

        if (u.equals("a@gmail.com") && p.equals("11111111"))
        {
            Intent i = new Intent(MainActivity.this, AdminActivity.class);
            startActivity(i);
            finish();
        }
    }
    public void checkDealer()
    {

        final FirebaseUser user=auth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("dealers");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())) {

                    Toast.makeText(getApplicationContext(),"dealer logged",Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, dealerScreen.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public void checkUser()
    {
        final FirebaseUser user=auth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("users");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())) {

                    Toast.makeText(getApplicationContext(),"user logged",Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, SecondActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

    }
    public void checkDeliveryboy()
    {

    }
    public void checkSupplier()
    {

    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progress=new ProgressDialog(this,R.style.AppCompatAlertDialogStyle);


        checkLoggedin();


        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        Login   =(Button)findViewById(R.id.login);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uname = username.getText().toString();
                pwd = password.getText().toString();

                if (uname.length() == 0)
                {
                    username.requestFocus();
                    username.setError("Please enter Mail Id");
                }

                else if (password.length() == 0)
                {
                    password.requestFocus();
                    password.setError("Please enter password");
                }
                else
                {


                    checkAdmin(uname,pwd);

                    progress.setMessage("Logging in.....");
                    progress.show();

                        auth.signInWithEmailAndPassword(uname, pwd)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                if (!task.isSuccessful())
                                {
                                    if (password.length() < 8)
                                    {
                                        password.setError("Password should be atleast 8 characters");
                                    }
                                    else
                                    {
                                        Toast.makeText(MainActivity.this, "login Failed" + task.getException(), Toast.LENGTH_LONG).show();
                                        progress.hide();
                                    }
                                }
                                else
                                {

                                    checkDealer();
                                    checkUser();
                                    checkDeliveryboy();
                                    checkSupplier();
                                }
                            }
                        });


                }
            }//END OF ONCLICK for login


        });//end of onclick listener of login

        Signup=(Button)findViewById(R.id.signup);
        Signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Signup.class);
                startActivity(i);
                finish();
            }
        });


    }
}

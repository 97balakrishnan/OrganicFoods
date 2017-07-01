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
    FirebaseUser user;
    ProgressDialog progress;
    DatabaseReference db;



    public void checkLoggedin()
    {
        auth=FirebaseAuth.getInstance();

        if(auth.getCurrentUser() != null) {


            user=auth.getCurrentUser();
            db=FirebaseDatabase.getInstance().getReference();
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.child("dealers").hasChild(user.getUid()))
                    {
                        startActivity(new Intent(MainActivity.this,dealerScreen.class));
                        finish();
                    }
                    else if(dataSnapshot.child("users").hasChild(user.getUid()))
                    {
                        startActivity(new Intent(MainActivity.this,SecondActivity.class));
                        finish();
                    }
                    else if(dataSnapshot.child("delboys").hasChild(user.getUid()))
                    {
                        startActivity(new Intent(MainActivity.this,delboyScreen.class));
                        finish();

                    }
                    else if(dataSnapshot.child("admin").hasChild(user.getUid()))
                    {
                        startActivity(new Intent(MainActivity.this,AdminActivity.class));
                        finish();

                    }
                    else if(dataSnapshot.child("suppliers").hasChild(user.getUid())){
                        startActivity(new Intent(MainActivity.this,SupplierActivity.class));
                        finish();
                    }
                    else
                    {
                        auth.signOut();
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        finish();

                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            startActivity(new Intent(getApplicationContext(), SecondActivity.class));
        }
    }

    public void checkAdmin()
    {

        final FirebaseUser user=auth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("admin");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())) {

                    Toast.makeText(getApplicationContext(),"admin logged",Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, AdminActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

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
        final FirebaseUser user=auth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("delboys");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())) {

                    Toast.makeText(getApplicationContext(),"delivery man logged",Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, delboyScreen.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


    }
    public void checkSupplier()
    {

        final FirebaseUser user=auth.getCurrentUser();
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference().child("suppliers");

        rootRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())) {

                    Toast.makeText(getApplicationContext(),"supplier logged",Toast.LENGTH_LONG).show();
                    intent = new Intent(MainActivity.this, SupplierActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });

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


                   // checkAdmin(uname,pwd);

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
                                    Toast.makeText(getApplicationContext(),"Hellolo",Toast.LENGTH_LONG).show();
                                    checkAdmin();
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

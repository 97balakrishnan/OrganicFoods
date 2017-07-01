package com.example.rajesh.organicfoods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addDelboy extends AppCompatActivity {
    static int flag=0;
    Dealer d;
    EditText Name,email,password,cpassword,mobile,address,dealerID;
    Button signup;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String Email,Password,Cpassword;
    DatabaseReference mdatabase;
    int ok=0;
    String uname,pwd;
    ProgressDialog progress;
    Intent intent;
    boolean flag1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delboy);

        firebaseAuth=FirebaseAuth.getInstance();

        firebaseUser=firebaseAuth.getCurrentUser();
        progress=new ProgressDialog(this);
        dealerID = (EditText)findViewById(R.id.DealerID);
        Name=(EditText)findViewById(R.id.Name);
        email=(EditText)findViewById(R.id.email);
        password=(EditText)findViewById(R.id.password);
        cpassword=(EditText)findViewById(R.id.cpassword);
        mobile=(EditText)findViewById(R.id.mobile);
        address=(EditText)findViewById(R.id.address);
        signup=(Button)findViewById(R.id.signup);
        final CheckBox cb =(CheckBox)findViewById(R.id.checkBox);
        final EditText et = (EditText)findViewById(R.id.DealerID);

        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("dealers");
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChild(firebaseUser.getUid())) {
                    d = dataSnapshot.child(firebaseUser.getUid()).getValue(Dealer.class);

                    uname=d.Email;
                    pwd=d.password;
                    Toast.makeText(getApplicationContext(),"HELLO"+uname+"  "+pwd,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        cb.setChecked(true);
        cb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isChecked())
                {
                    et.setEnabled(true);
                    et.setBackgroundColor(Color.TRANSPARENT);
                }
                else
                {
                    et.setEnabled(false);
                    et.setBackgroundColor(Color.GRAY);
                }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cb.isEnabled() && dealerID.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Enter dealerID", Toast.LENGTH_SHORT).show();
                    dealerID.requestFocus();
                }

      else if(dealerID.length()==0)
                {
                    Toast.makeText(getApplicationContext(), "Enter Dealer ID", Toast.LENGTH_SHORT).show();
                    dealerID.requestFocus();

                }
                else if (Name.length() != 0) {
                    if (email.length() != 0) {
                        if (password.length() != 0 && password.length() >= 8) {
                            Email = email.getText().toString().trim();
                            Password = password.getText().toString().trim();
                            Cpassword = cpassword.getText().toString().trim();

                            if (cpassword.length() != 0) {
                                if (Cpassword.equals(Password)) {
                                    if (mobile.length() != 0 && mobile.length() == 10) {
                                        if(address.length()!=0)
                                            ok = 1;
                                        else
                                        {
                                            Toast.makeText(getApplicationContext(), "Enter your Address", Toast.LENGTH_SHORT).show();
                                            address.requestFocus();
                                        }


                                    } else {
                                        Toast.makeText(getApplicationContext(), "Enter Mobile number", Toast.LENGTH_SHORT).show();
                                        mobile.requestFocus();
                                    }
                                } else {
                                    Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                                    cpassword.requestFocus();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Enter confirm Password", Toast.LENGTH_SHORT).show();
                                cpassword.requestFocus();
                            }

                        } else {
                            Toast.makeText(getApplicationContext(), "Enter Password", Toast.LENGTH_SHORT).show();
                            password.requestFocus();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Enter email-ID", Toast.LENGTH_SHORT).show();
                        email.requestFocus();
                    }


                } else {
                    Toast.makeText(getApplicationContext(), "Enter your Name", Toast.LENGTH_SHORT).show();
                    Name.requestFocus();
                }

                if (ok == 1) {
                    firebaseAuth.signOut();
                    progress.setMessage("Creating an Account...");
                    firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(addDelboy.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (!task.isSuccessful()) {
                                Toast.makeText(addDelboy.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            } else {
                                mdatabase= FirebaseDatabase.getInstance().getReference();
                                String s=mdatabase.child("delboys").push().getKey();
                                firebaseUser=firebaseAuth.getCurrentUser();

                                delboy user;
                                if(cb.isChecked()) {
                                     user = new delboy(dealerID.getText().toString(),Name.getText().toString(), mobile.getText().toString(), email.getText().toString(), password.getText().toString(), address.getText().toString());
                                }
                                else
                                {
                                    user = new delboy("adminDelboy",Name.getText().toString(), mobile.getText().toString(), email.getText().toString(), password.getText().toString(), address.getText().toString());
                                }
                                mdatabase.child("delboys").child(firebaseUser.getUid()).setValue(user);
                                flag=1;

                                if(cb.isChecked()) {
                                    mdatabase.child("DealerDelboy").child(dealerID.getText().toString()).push().setValue(Name.getText().toString());
                                }
                                else
                                {
                                    mdatabase.child("DealerDelboy").child("adminDelboys").push().setValue(Name.getText().toString());
                                }
                                Toast.makeText(getApplicationContext(),"Delivery boy added",Toast.LENGTH_SHORT);

                                dealerID.setText("");
                                Name.setText("");
                                email.setText("");
                                password.setText("");
                                cpassword.setText("");
                                mobile.setText("");
                                address.setText("");
                                firebaseAuth.signOut();

                                firebaseAuth.signInWithEmailAndPassword(uname, pwd);

                            }
                        }
                    });
                }

            }
        });


    }
}

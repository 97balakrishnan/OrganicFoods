package com.example.rajesh.organicfoods;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDealer extends AppCompatActivity {
    static int flag=0;
    EditText Name,email,password,cpassword,mobile,address,dealerID;
    Button signup;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    String Email,Password,Cpassword;
    DatabaseReference mdatabase;
    int ok=0;

    ProgressDialog progress;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dealer);


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

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dealerID.length()==0)
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
                    progress.setMessage("Creating an Account...");
                    firebaseAuth.createUserWithEmailAndPassword(Email, Password).addOnCompleteListener(AddDealer.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Toast.makeText(AddDealer.this, "createUserWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                            ///progressBar.setVisibility(View.GONE);
                            // If sign in fails, display a message to the user. If sign in succeeds
                            // the auth state listener will be notified and logic to handle the
                            // signed in user can be handled in the listener.
                            if (!task.isSuccessful()) {
                                Toast.makeText(AddDealer.this, "Authentication failed." + task.getException(),
                                        Toast.LENGTH_LONG).show();
                                progress.dismiss();
                            } else {
                                mdatabase= FirebaseDatabase.getInstance().getReference();
                                String s=mdatabase.child("dealers").push().getKey();
                                firebaseUser=firebaseAuth.getCurrentUser();

                                Dealer user = new Dealer(dealerID.getText().toString(),Name.getText().toString(),mobile.getText().toString(),email.getText().toString(),password.getText().toString(),address.getText().toString());
                                mdatabase.child("dealers").child(firebaseUser.getUid()).setValue(user);
                                flag=1;
                                Toast.makeText(getApplicationContext(),"DEALER account CREATED SUCCESSFULLY",Toast.LENGTH_SHORT);
                                firebaseAuth.signOut();
                                dealerID.setText("");
                                Name.setText("");
                                email.setText("");
                                password.setText("");
                                cpassword.setText("");
                                mobile.setText("");
                                address.setText("");

                            }
                        }
                    });
                }

            }
        });
        Button login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(AddDealer.this,MainActivity.class);
                startActivity(i);
            }
        });

    }

}

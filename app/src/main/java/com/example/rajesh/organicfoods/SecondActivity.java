package com.example.rajesh.organicfoods;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SecondActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
static int ship=1;
    FirebaseUser user;
    FirebaseAuth auth;
    NavigationView navigationView;
    String customerName,Mobile,Mail,Address;
    TextView cname,mobile;
    CustomerDb customerDb;
    Cursor c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        customerDb=new CustomerDb(this);

         auth=FirebaseAuth.getInstance();
         user=auth.getCurrentUser();
        DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
        //String s=mDatabase.child("users").push().getKey();
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                c=customerDb.getDb();

                Customer customer = dataSnapshot.child(user.getUid()).getValue(Customer.class);
                customerName=customer.customerName;
                Mobile=customer.Mobile;
                Mail=customer.Email;
                Address=customer.Address;
                System.out.println(customer.customerName+" "+customer.Mobile+" "+customer.Email+" "+customer.Address);

                if(c==null){
                    customerDb.addDetails(customerName,Mail,Mobile,Address);
                }
                View headerView = navigationView.getHeaderView(0);
                cname = (TextView) headerView.findViewById(R.id.Header);
                // cname.setTypeface(tf);
                cname.setText(customerName);
                mobile = (TextView) headerView.findViewById(R.id.mobile);
                mobile.setText(Mobile);
                // ...
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                System.out.println("loadPost:onCancelled"+ databaseError.toException());
                // ......
            }
        };
        mDatabase.child("users").addValueEventListener(postListener);

              // System.out.println(customerName);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

         navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setItemIconTintList(null);


        //cname.setTextColor((rgb(255,215,0)));

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finish();
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.second, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.Products) {
            Intent i=new Intent(SecondActivity.this,ProductDisplay.class);
            startActivity(i);
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.ViewCart) {
            startActivity(new Intent(SecondActivity.this,CartDisplay.class));


        } else if (id == R.id.feedback) {
            startActivity(new Intent(SecondActivity.this,FeedBackActivity.class));

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.logout) {

            auth.signOut();
            //closing activity
            finish();
            //starting login activity
            startActivity(new Intent(this, MainActivity.class));

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

package com.example.rajesh.organicfoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class dealerScreen extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    String name,number;
    final FirebaseAuth auth= FirebaseAuth.getInstance();
    final FirebaseUser user=auth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer_screen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("dealers");


        db.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild(user.getUid())) {

                    Dealer dealer = dataSnapshot.child(user.getUid()).getValue(Dealer.class);
                    name=dealer.dealerName;
                    number=dealer.Mobile;
                }

                View headerView = navigationView.getHeaderView(0);
                TextView cname = (TextView) headerView.findViewById(R.id.Header);
                cname.setText(name);
                TextView mobile = (TextView) headerView.findViewById(R.id.mobile);
                mobile.setText(number);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }

        });


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.dealer_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.approve_users) {
            startActivity(new Intent(dealerScreen.this, approve_user.class));
            finish();

        } else if (id == R.id.add_delboy) {
            startActivity(new Intent(dealerScreen.this, addDelboy.class));
            finish();

        } else if (id == R.id.delivery_status) {

        } else if (id == R.id.log_out) {
            auth.signOut();
            startActivity(new Intent(dealerScreen.this, MainActivity.class));
            finish();

        } else if (id == R.id.supplier_details) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}

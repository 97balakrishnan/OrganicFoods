package com.example.rajesh.organicfoods;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CartDisplay extends AppCompatActivity {
    ListView cartListview;
    int length;
    StringBuilder sb,userBuilder;
    static int edit=0;
    ArrayList<CartClass> item;
    Cursor c, userCursor;
    static int flag = 0;
    CartDb cart;
    CustomerDb user;
    TextView amount;
    CartDisplayAdapter adapter;
    ImageButton delete;
    Button placeOrder;
    FirebaseUser fuser;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sb=new StringBuilder();
        userBuilder=new StringBuilder();
        FirebaseAuth auth=FirebaseAuth.getInstance();
         fuser=auth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        cart = new CartDb(getApplicationContext());
        c = cart.getDb();
        user = new CustomerDb(getApplicationContext());
        userCursor = user.getDb();
        if (c != null) {
            setContentView(R.layout.activity_cart_display);
            cartListview = (ListView) findViewById(R.id.cartListview);
            item = new ArrayList<>();
            item.add(new CartClass(c.getString(0), c.getString(1), c.getString(3), c.getString(2)));
            while (c.moveToNext()) {
                System.out.println(c.getString(1) + " " + c.getString(3) + " " + c.getString(2));
                item.add(new CartClass(c.getString(0), c.getString(1), c.getString(3), c.getString(2)));

            }
                length=item.size();
            adapter = new CartDisplayAdapter(this, R.layout.cartitemdisplay, item);
            cartListview.setAdapter(adapter);
            cartListview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                    Toast.makeText(getApplicationContext(), item.get(position).getProductId().toString(), Toast.LENGTH_SHORT).show();


                }
            });

        } else
            setContentView(R.layout.noitems);


        c = cart.getDb();
        if (c != null) {
            //amount = (TextView) findViewById(R.id.priceAmount);

            int am = Integer.parseInt(c.getString(2));
            while (c.moveToNext())
                am += Integer.parseInt(c.getString(2));

            c.moveToFirst();
            //amount.setText("Rs" + am);
            placeOrder = (Button) findViewById(R.id.placeorder);
            placeOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int rno = 1;
                    Calendar cal = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("dd:MM:yyyy_hh:mm:ss"); //called without pattern
                    String Date = df.format(cal.getTime());
                    System.out.println("Product" + rno + "\n" + c.getString(0) + "\n" + c.getString(1) + "\n" + c.getString(2) + c.getString(3));
                    String pno = "Product" + rno;

                    sb.append(pno+"\n\tProduct ID:"+c.getString(0)+"\n\tProductName:"+c.getString(1)+"\n\tProduct Price:"+c.getString(2)+"\n\tProduct Quantity:"+c.getString(3)+"\n");
                    userBuilder.append("Customer Name:"+userCursor.getString(0)+"\nCustomer MailId:"+userCursor.getString(1)+"\nMobile:"+userCursor.getString(2)+"\nDelivery Address:"+userCursor.getString(3));
                    mDatabase = FirebaseDatabase.getInstance().getReference();

                    System.out.println("Order inserted in db");
                    while (c.moveToNext()) {
                        rno++;
                        pno = "Product" + rno;
                        System.out.println("Product" + rno++ + "\n" + c.getString(0) + "\n" + c.getString(1) + "\n" + c.getString(2) + c.getString(3));
                        sb.append(pno+"\n\tProduct ID:"+c.getString(0)+"\n\tProductName:"+c.getString(1)+"\n\tProduct Price:"+c.getString(2)+"\n\tProduct Quantity:"+c.getString(3)+"\n");
                      System.out.println("Order inserted in db");

                    }
                    OrderItem orderitem = new OrderItem(sb.toString(),userBuilder.toString(),Date, "Processing");
                    mDatabase.child("Order").child(Date).setValue(orderitem);
                    Toast.makeText(getApplicationContext(), "Your order has been placed...", Toast.LENGTH_SHORT).show();
                }
            });
        }

        handle();
    }

    void handle() {
        final Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                Cursor f = cart.getDb();
                if (f == null)
                    setContentView(R.layout.noitems);

                h.postDelayed(this, 1000);
            }
        }, 1000);
    }








}



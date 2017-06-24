package com.example.rajesh.organicfoods;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeliveredItems extends AppCompatActivity {
    TextView productname;
    ListView viewOrders;
    ArrayList<OrderItem>list;
    DatabaseReference mDatabase;
    ViewOrderAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivered_items);
        list=new ArrayList<OrderItem>();
        viewOrders=(ListView)findViewById(R.id.viewOrders);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot data:dataSnapshot.child("Order").getChildren()){
                    OrderItem p=data.getValue(OrderItem.class);
                    System.out.println(p.OrderStatus+" "+p.UserDetails+" \n"+p.ProductDetails+"\n"+p.Orderdate);
                    if(p.OrderStatus.equals("Delivered")){
                        System.out.println("Order recieved");
                        list.add(new OrderItem(p.ProductDetails,p.UserDetails,p.Orderdate,p.OrderStatus));
                        System.out.println(list.size());

                    }

                }
                adapter=new ViewOrderAdapter(DeliveredItems.this,R.layout.vieworderitems,list);
                viewOrders.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

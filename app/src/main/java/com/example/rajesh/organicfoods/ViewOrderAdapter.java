package com.example.rajesh.organicfoods;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rajesh on 16/06/17.
 */

public class ViewOrderAdapter extends ArrayAdapter<OrderItem> {

    Context context;

    public ViewOrderAdapter(Context context, int resourceId,
                          ArrayList<OrderItem> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    /*private view holder class*/
    private class ViewHolder {
        TextView userDetails,orderdate,orderstatus,productDetails;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        OrderItem rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.vieworderitems, null);
            holder = new ViewHolder();
            holder.userDetails = (TextView) convertView.findViewById(R.id.userdetails);
            holder.orderdate = (TextView) convertView.findViewById(R.id.orderdate);
            holder.orderstatus = (TextView) convertView.findViewById(R.id.orderstatus);
            holder.productDetails=(TextView)convertView.findViewById(R.id.productdetails);

            // convertView.setOnClickListener(this);

            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();
        holder.userDetails.setText(rowItem.UserDetails);
        holder.orderdate.setText("Order Date: "+rowItem.Orderdate);
        holder.productDetails.setText(rowItem.ProductDetails);
        holder.orderstatus.setText(rowItem.OrderStatus);
        //holder.userDetails.setClickable(false);
        //holder.orderstatus.setClickable(false);
        //holder.orderdate.setClickable(false);
        System.out.println(rowItem.UserDetails+"from Adapter class");



        return convertView;
    }




}

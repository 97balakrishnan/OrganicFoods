package com.example.rajesh.organicfoods;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by rajesh on 13/06/17.
 */

public class CartDisplayAdapter extends ArrayAdapter<CartClass> {
    Context context;
    CartDb cart;
    ArrayList<CartClass>items=new ArrayList<>();
    public CartDisplayAdapter(Context context, int resourceId,
                              ArrayList<CartClass> items) {
        super(context, resourceId, items);
        this.context = context;
        this.items=items;
    }







    /*private view holder class*/
    private class ViewHolder {
        Button editCart;
        ImageButton delete;
        TextView pName, pQuantity, pPrice;

    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        final CartClass rowItem = getItem(position);
        cart=new CartDb(context);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.cartitemdisplay, null);
            holder = new ViewHolder();
            holder.pName = (TextView) convertView.findViewById(R.id.cartpname);
            holder.pQuantity = (TextView) convertView.findViewById(R.id.cartpquantity);
            holder.pPrice = (TextView) convertView.findViewById(R.id.cartpprice);
            holder.editCart = (Button) convertView.findViewById(R.id.editcart);
            holder.delete=(ImageButton)convertView.findViewById(R.id.delete);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.pName.setClickable(false);
        holder.pQuantity.setClickable(false);
        holder.delete.setClickable(true);
        holder.editCart.setClickable(false);
        holder.pName.setText(rowItem.ProductName);
        holder.pPrice.setText("Price is "+rowItem.Productprice);
        holder.pQuantity.setText("Quantity is "+rowItem.ProductQuantity);
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int rowid = position;
                System.out.println("clicked....."+items.get(position).ProductQuantity);
                cart.deleteItem(items.get(position).ProductQuantity);
                //Toast.makeText(context,"clicked",Toast.LENGTH_SHORT).show();
                items.remove(position);
                notifyDataSetChanged();
                CartDisplay.flag=1;

            }
        });

        //holder.pName.setText(rowItem.ProductName);

        return convertView;
    }

}
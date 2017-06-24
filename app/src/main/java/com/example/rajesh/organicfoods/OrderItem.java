package com.example.rajesh.organicfoods;

/**
 * Created by rajesh on 15/06/17.
 */

public class OrderItem {
    public String UserDetails,ProductDetails,Orderdate,OrderStatus;



    OrderItem(String productDetails,String userDetails,String orderdate,String orderStatus){
        UserDetails=userDetails;
        ProductDetails=productDetails;
        Orderdate=orderdate;
        OrderStatus=orderStatus;

    }
    OrderItem(){}
}

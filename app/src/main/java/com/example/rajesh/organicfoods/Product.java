package com.example.rajesh.organicfoods;

/**
 * Created by rajesh on 12/06/17.
 */

public class Product {
public String ProductName,ProductQuantity,ProductId,Productprice,quantity,Productimage;

    Product(String pname,String pid,String quantity,String price,String image){
        ProductName=pname;
        ProductQuantity=quantity;
        ProductId=pid;
        Productprice=price;
        Productimage=image;
    }


    Product(){}



}

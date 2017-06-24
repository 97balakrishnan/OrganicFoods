package com.example.rajesh.organicfoods;

/**
 * Created by rajesh on 13/06/17.
 */

public class CartClass {
    public String ProductName,ProductQuantity,ProductId,Productprice,Productimage;

    public  CartClass(String pid,String pname,String quantity,String price){
        ProductId=pid;
        ProductName=pname;
        ProductQuantity=quantity;
        Productprice=price;
    }

    CartClass(){}

    public void setProductId(String pid){
        ProductId=pid;
    }
    public void setProductName(String pname){
        ProductName=pname;
    }
    public void setProductprice(String productQuantity){
        ProductQuantity=productQuantity;
    }
    public void setProductimage(String pimage){
        Productimage=pimage;
    }

    public String getProductId(){return ProductId;}
    public String getProductName(){
        return ProductName;
    }
    public String getProductprice(){
        return Productprice;
    }

}

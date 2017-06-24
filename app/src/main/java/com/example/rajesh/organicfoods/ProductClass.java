package com.example.rajesh.organicfoods;

/**
 * Created by rajesh on 13/06/17.
 */

public class ProductClass {
    public String ProductName,ProductQuantity,ProductId,Productprice,Productimage;

       public  ProductClass(String pname,String pid,String quantity,String price,String image){
            ProductName=pname;
            ProductQuantity=quantity;
            ProductId=pid;
            Productprice=price;
            Productimage=image;
        }


        ProductClass(){}


    public void setProductName(String pname){
        ProductName=pname;
    }
    public void setProductprice(String productQuantity){
        ProductQuantity=productQuantity;
    }
    public void setProductimage(String pimage){
        Productimage=pimage;
    }

    public String getProductName(){
        return ProductName;
    }
    public String getProductprice(){
        return Productprice;
    }
    public String getProductimage(){
        return Productimage;
    }

}


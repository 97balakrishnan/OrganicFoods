package com.example.rajesh.organicfoods;

/**
 * Created by rajesh on 11/06/17.
 */

public class Customer {
    public String customerName;
    public String Mobile;
    public String password;
    public String Email;
    public String Address;

    public Customer(String cname,String mobile,String email,String pwd,String address){
        customerName=cname;
        Mobile=mobile;
        this.Email=email;
        password=pwd;
        Address=address;
    }

    public  Customer(){}

}

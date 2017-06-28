package com.example.rajesh.organicfoods;

/**
 * Created by User on 24-06-2017.
 */

public class Dealer {
    public String dealerName;
    public String Mobile;
    public String password;
    public String Email;
    public String Address;
    public String DealerID;

    public Dealer(String dID,String dname,String mobile,String email,String pwd,String address){
        dealerName=dname;
        Mobile=mobile;
        this.Email=email;
        password=pwd;
        Address=address;
        DealerID=dID;
    }

    public  Dealer(){}

}

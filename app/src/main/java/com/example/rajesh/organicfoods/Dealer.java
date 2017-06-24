package com.example.rajesh.organicfoods;

/**
 * Created by User on 24-06-2017.
 */

public class Dealer {
    public String customerName;
    public String Mobile;
    public String password;
    public String Email;
    public String Address;
    public String DealerID;

    public Dealer(String dID,String cname,String mobile,String email,String pwd,String address){
        customerName=cname;
        Mobile=mobile;
        this.Email=email;
        password=pwd;
        Address=address;
        DealerID=dID;
    }

    public  Dealer(){}

}

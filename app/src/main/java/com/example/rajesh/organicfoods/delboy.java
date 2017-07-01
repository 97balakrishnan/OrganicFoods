package com.example.rajesh.organicfoods;

/**
 * Created by User on 01-07-2017.
 */

public class delboy {
    public String Name;
    public String Mobile;
    public String password;
    public String Email;
    public String Address;
    public String dealerID;

    public delboy(String dID,String ename,String mobile,String email,String pwd,String address){
        Name=ename;
        Mobile=mobile;
        this.Email=email;
        password=pwd;
        Address=address;
        dealerID=dID;
    }

    public  delboy(){}

}

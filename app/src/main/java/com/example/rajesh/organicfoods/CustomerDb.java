package com.example.rajesh.organicfoods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajesh on 15/06/17.
 */

public class CustomerDb extends SQLiteOpenHelper{

    SQLiteDatabase db;
    String ename,pname,relation,dob;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CustomerDb";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Customer";

    // Contacts Table Columns names
    private static final String Customer_Name = "Customer_Name";
    private static final String Customer_Mail = "Customer_Mail";
    private static final String Customer_Mobile = "Customer_Mobile";
    private static final String Customer_Address = "Customer_Address";

    public CustomerDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        //String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
        // + Product_Id + " TEXT," + Product_Name + " TEXT," + Product_Price +"TEXT,"+ Product_Quantity +"TEXT" +")" ;
        db.execSQL("create table IF NOT EXISTS Customer(Customer_Name varchar,Customer_Mail varchar,Customer_Mobile varchar,Customer_Address varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addDetails(String cName,String cMail,String cMobile,String cAddress){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Customer_Name",cName); // Contact Name
        values.put("Customer_Mail",cMail);
        values.put("Customer_Mobile",cMobile);
        values.put("Customer_Address",cAddress);

        db.insert(TABLE_CONTACTS, null, values);
        //db.close();
        System.out.println("Successfully User Added");
    }

    public Cursor getDb(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Customer",null);

        if (cursor != null& cursor.moveToFirst())
        {
            System.out.println("Cursor is not null from Customer DB"+cursor.getString(0)+cursor.getString(1));

            return cursor;
        }
        else {
            System.out.println("Cursor null");
            return null;
        }

    }
}

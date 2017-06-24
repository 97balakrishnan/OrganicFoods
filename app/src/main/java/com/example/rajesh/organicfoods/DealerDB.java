package com.example.rajesh.organicfoods;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by User on 24-06-2017.
 */

public class DealerDB extends SQLiteOpenHelper {

    SQLiteDatabase db;
    String ename,pname,relation,dob;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "DealerDb";

    // Contacts table name
    private static final String TABLE_CONTACTS = "Dealer";

    // Contacts Table Columns names
    private static final String Dealer_Name = "Dealer_Name";
    private static final String Dealer_Mail = "Dealer_Mail";
    private static final String Dealer_Mobile = "Dealer_Mobile";
    private static final String Dealer_Address = "Dealer_Address";
    private static final String Dealer_ID = "Dealer_ID";

    public DealerDB(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        //String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
        // + Product_Id + " TEXT," + Product_Name + " TEXT," + Product_Price +"TEXT,"+ Product_Quantity +"TEXT" +")" ;
        db.execSQL("create table IF NOT EXISTS Dealer(Dealer_ID varchar,Dealer_Name varchar,Dealer_Mail varchar,Dealer_Mobile varchar,Dealer_Address varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addDetails(String dID,String dName,String dMail,String dMobile,String dAddress){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Dealer_ID",dID);
        values.put("Dealer_Name",dName); // Contact Name
        values.put("Dealer_Mail",dMail);
        values.put("Dealer_Mobile",dMobile);
        values.put("Dealer_Address",dAddress);

        db.insert(TABLE_CONTACTS, null, values);
        //db.close();
        System.out.println("Successfully Dealer Added");
    }

    public Cursor getDb(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from Dealer",null);

        if (cursor != null& cursor.moveToFirst())
        {
            System.out.println("Cursor is not null from Dealer DB"+cursor.getString(0)+cursor.getString(1));

            return cursor;
        }
        else {
            System.out.println("Cursor null");
            return null;
        }

    }
}

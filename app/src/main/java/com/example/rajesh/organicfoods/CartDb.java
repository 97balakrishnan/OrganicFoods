package com.example.rajesh.organicfoods;

/**
 * Created by rajesh on 13/06/17.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by rajesh on 09/06/17.
 */

public class CartDb extends SQLiteOpenHelper{
    SQLiteDatabase db;
    String ename,pname,relation,dob;
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "CartDb";

    // Contacts table name
    private static final String TABLE_CONTACTS = "CartItems";

    // Contacts Table Columns names
    private static final String Product_Name = "ProductName";
    private static final String Product_Id = "ProductId";
    private static final String Product_Price = "ProductPrice";
    private static final String Product_Quantity = "ProductQuantity";

    public CartDb(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        //String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
               // + Product_Id + " TEXT," + Product_Name + " TEXT," + Product_Price +"TEXT,"+ Product_Quantity +"TEXT" +")" ;
        db.execSQL("create table IF NOT EXISTS CartItems(ProductId varchar,ProductName varchar,ProductPrice varchar,ProductQuantity varchar)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addItem(String productId,String productName,String price,String quantity){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ProductId",productId); // Contact Name
        values.put("ProductName",productName);
        values.put("ProductPrice",price);
        values.put("ProductQuantity",quantity);

        db.insert(TABLE_CONTACTS, null, values);
        //db.close();
        System.out.println("Successfully Item Added");
    }

    public Cursor getDb(){
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from CartItems",null);

        if (cursor != null& cursor.moveToFirst())
        {
            System.out.println("Cursor is not null");//+cursor.getString(0)+cursor.getString(1));

            return cursor;
        }
        else {
            System.out.println("Cursor null");
            return null;
        }

    }
    public void deleteItem(String pq){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from CartItems where ProductQuantity='"+pq+"' ");
        System.out.println("item deleted");
    }
    public void UpdateItem(String pq,String price,String pid){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("update CartItems set ProductQuantity='"+pq+"',ProductPrice='"+price+"' where ProductId='"+pid+"' ");
        System.out.println("item updated");
    }

}


package com.hfad.a816018640_and_816015043;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class SqlDatabase extends SQLiteOpenHelper {
    private static final String DB_NAME="a2Data";
    private static final int DB_VERSION = 1;

     SqlDatabase(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
         updateMyDatabase(db,oldVersion,newVersion);
    }

    private static void insertProduct(SQLiteDatabase db,String Name,int stockonhand,int stockintransit,
                                      int price,int reorderquantity,int reorderamount){
        ContentValues productValues=new ContentValues();
        productValues.put("Name",Name);
        productValues.put("StockOnHand",stockonhand);
        productValues.put("StockInTransit",stockintransit);
        productValues.put("Price",price);
        productValues.put("ReorderQuantity",reorderquantity);
        productValues.put("ReorderAmount",reorderamount);
        db.insert("Product",null,productValues);
    }

    private void updateMyDatabase(SQLiteDatabase db,int oldVersion,int newVersion){
         if(oldVersion<1){
             db.execSQL("CREATE TABLE Product(_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                     +"Name TEXT,"+"StockOnHand INTEGER,"+"StockInTransit INTEGER,"+"Price INTEGER,"
                     +"ReorderQuantity INTEGER,"+"ReorderAmount INTEGER);");
             insertProduct(db,"Intel Core i9",5,10,450,5,10);
             insertProduct(db,"NVIDIA RTX 3090",3,0,1500,5,10);
         }
        if(oldVersion<2){
            db.execSQL("ALTER TABLE Product ADD COLUMN FAVORITE NUMERIC");
        }
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE Product ADD COLUMN DIRTY BIT default 'FALSE'");
        }
    }
    }


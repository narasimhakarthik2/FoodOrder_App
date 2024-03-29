package com.k.menu.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.k.menu.Cart;
import com.k.menu.model.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Karthik on 15-01-2018.
 */

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "EatitDB.db";
    private static final int DB_VER =1;
    public Database(Context context) {
        super(context ,DB_NAME,null,DB_VER);
    }

    public List<Order> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[]sqlSelect ={"ProductName","ProductId","Quantity","price"};
        String sqlTable="OrderDetail";

        qb.setTables(sqlTable);

        Cursor c =qb.query(db,sqlSelect,null,null,null,null,null);

        final List<Order>result =new ArrayList<>();
        if(c.moveToFirst())
        {
            do {
                result.add(new Order(c.getString(c.getColumnIndex("ProductName")),
                   c.getString(c.getColumnIndex("ProductId")),
                        c.getString(c.getColumnIndex("Quantity")),
                            c.getString(c.getColumnIndex("Price"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    public void addToCart(Order order){

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("INSERT INTO OrderDetail(Productid,ProductName,Quantity,Price) VALUES('%s','%s','%s','%s');",
        order.getProductid(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice());
        db.execSQL(query);
    }
    public void clearCart(){

        SQLiteDatabase db = getWritableDatabase();
        String query = String.format("DELETE FROM OrderDetail");
        db.execSQL(query);
    }
}

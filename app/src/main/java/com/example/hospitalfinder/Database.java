package com.example.hospitalfinder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class Database extends SQLiteOpenHelper {
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "create table users(username text,email text, password text)";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void registerUser(String username, String email, String password) {
        ContentValues cv = new ContentValues();
        cv.put("username", username);
        cv.put("email", email);
        cv.put("password", password);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("users", null, cv);
        db.close();
    }

    public int login(String username, String password) {
        int result = 0;
        String str[] = new String[2];
        str[0] = username;
        str[1] = password;
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select * from users where username=? and password=?", str);
        if (c.moveToFirst()) {
            result = 1;
        }
        return result;
    }

    public ArrayList getCartData(String username, String lab) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String str[] = new String[2];
        str[0] = username;
        str[1] = lab;
        Cursor c = db.rawQuery("select * from cart where username=? and lab=?", str);
        if (c.moveToFirst()) {
            do {
                String product = c.getString(1);
                String price = c.getString(2);
                arr.add(product + "$" + price);
            } while (c.moveToNext());
        }
        db.close();
        return arr;
    }

    public ArrayList<String> getOrderDetails(String username) {
        ArrayList<String> arr = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = null;

        try {
            String[] selectionArgs = {username};
            // Use specific column names instead of *
            c = db.rawQuery("SELECT column1, column2, column3, column4, column5, column6, column7, column8 FROM orderplace WHERE username = ?", selectionArgs);

            if (c.moveToFirst()) {
                do {
                    // Concatenate column values with "$" as a delimiter
                    String orderDetail = c.getString(0) + "$" + c.getString(1) + "$" + c.getString(2) + "$" +
                            c.getString(3) + "$" + c.getString(4) + "$" + c.getString(5) + "$" +
                            c.getString(6) + "$" + c.getString(7);
                    arr.add(orderDetail);
                } while (c.moveToNext());
            }
        } catch (Exception e) {
            // Handle exceptions (e.g., log or show error)
            e.printStackTrace();
        } finally {
            if (c != null) {
                c.close(); // Close the cursor to avoid memory leaks
            }
            db.close(); // Close the database
        }

        return arr;
    }
}


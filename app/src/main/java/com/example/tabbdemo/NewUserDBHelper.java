package com.example.tabbdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class NewUserDBHelper  extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "users_db";


    public NewUserDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(NewUserModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NewUserModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public boolean addUser(String fristname, String lastname,String phone_number) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(NewUserModel.COLUMN_FRIST_NAME, fristname);
        values.put(NewUserModel.COLUMN_LAST_NAME, lastname);
        values.put(NewUserModel.COLUMN_PHONE_NUMBER, phone_number);

        // insert row
        long id = db.insert(NewUserModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return true;
    }


    public Cursor getUser(long id) {
        id = id+1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from newusertable where id="+id+"", null );

        return res;
    }

    public Integer deleteUser(String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("newusertable",
                "title = ? ",
                new String[]{title});
    }



    public Cursor getAllUsers() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from newusertable", null );

        return res;
    }

}

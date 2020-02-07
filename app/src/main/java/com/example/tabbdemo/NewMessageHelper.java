package com.example.tabbdemo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NewMessageHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "messages_db";


    public NewMessageHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(NewMessageModel.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + NewMessageModel.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }
    public boolean insertMessage(String title, String content) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        // `id` and `timestamp` will be inserted automatically.
        // no need to add them
        values.put(NewMessageModel.COLUMN_Title, title);
        values.put(NewMessageModel.COLUMN_Content, content);

        // insert row
        long id = db.insert(NewMessageModel.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return true;
    }


    public Cursor getMessage(long id) {
        id = id+1;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from newmessage where id="+id+"", null );

        return res;
    }

    public Integer deleteMessage (String title) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("newmessage",
                "title = ? ",
                new String[]{title});
        }



    public ArrayList<String> getAllMessages() {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from newmessage", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(NewMessageModel.COLUMN_Title)));
            res.moveToNext();
        }
        return array_list;
    }

}

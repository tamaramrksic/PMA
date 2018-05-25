package com.example.ftn.showbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ftn.showbook.model.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "showbook_db";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {

        // create notes table
        db.execSQL(UserDB.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.TABLE_NAME);

        // Create tables again
        onCreate(db);
    }

    public Long insertUser(String username, String firstName, String lastName, String address,String location,
                           Integer maxDistance, String facilityType, boolean commentNotification) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(UserDB.COLUMN_USERNAME, username);
        values.put(UserDB.COLUMN_FIRSTNAME, firstName);
        values.put(UserDB.COLUMN_LASTNAME, lastName);
        values.put(UserDB.COLUMN_ADDRESS, address);
        values.put(UserDB.COLUMN_LOCATION, location);
        values.put(UserDB.COLUMN_MAXDISTANCE, maxDistance);
        values.put(UserDB.COLUMN_FACILITYTYPE, facilityType);
        values.put(UserDB.COLUMN_COMMENT_NOTIFICATION, commentNotification);


        // insert row
        long id = db.insert(UserDB.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public UserDB getUserByUsername(String username) {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(UserDB.TABLE_NAME,
                null,
                UserDB.COLUMN_USERNAME + "=?",
                new String[]{String.valueOf(username)}, null, null, null, null);

        UserDB userDB = null;

        if (cursor.moveToFirst()) {

            System.out.println("cursor count" + cursor.getCount());
            // prepare UserDB object
            boolean comment_notification = cursor.getInt(cursor.getColumnIndex(UserDB.COLUMN_COMMENT_NOTIFICATION)) > 0;
            userDB = new UserDB(
                    cursor.getInt(cursor.getColumnIndex(UserDB.COLUMN_ID)),
                    cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_USERNAME)),
                    cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_FIRSTNAME)),
                    cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_LASTNAME)),
                    cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_ADDRESS)),
                    cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_LOCATION)),
                    cursor.getInt(cursor.getColumnIndex(UserDB.COLUMN_MAXDISTANCE)),
                    cursor.getString(cursor.getColumnIndex(UserDB.COLUMN_FACILITYTYPE)),
                    comment_notification);
        }

        // close the db connection
        cursor.close();

        return userDB;
    }
}

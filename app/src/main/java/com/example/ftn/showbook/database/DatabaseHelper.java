package com.example.ftn.showbook.database;

import android.content.ContentValues;
import android.content.Context;
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

    public Long insertFacility(String name, String type, String address, String location,Double latitude,
                           Double longitude) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FacilityDB.COLUMN_NAME, name);
        values.put(FacilityDB.COLUMN_TYPE, type);
        values.put(FacilityDB.COLUMN_ADDRESS, address);
        values.put(FacilityDB.COLUMN_LOCATION, location);
        values.put(FacilityDB.COLUMN_LATITUDE, latitude);
        values.put(FacilityDB.COLUMN_LONGITUDE, longitude);



        // insert row
        long id = db.insert(FacilityDB.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }
}

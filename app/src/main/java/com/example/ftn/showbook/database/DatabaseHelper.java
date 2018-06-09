package com.example.ftn.showbook.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.ftn.showbook.model.User;

import java.util.ArrayList;
import java.util.List;

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
        db.execSQL(FacilityDB.CREATE_TABLE);
    }



    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + UserDB.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + FacilityDB.TABLE_NAME);
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


    public Long insertFacility(String name, String type, String address, String location,String latitude,
                           String longitude, String backId) {
        // get writable database as we want to write data
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(FacilityDB.COLUMN_NAME, name);
        values.put(FacilityDB.COLUMN_TYPE, type);
        values.put(FacilityDB.COLUMN_ADDRESS, address);
        values.put(FacilityDB.COLUMN_LOCATION, location);
        values.put(FacilityDB.COLUMN_LATITUDE, latitude);
        values.put(FacilityDB.COLUMN_LONGITUDE, longitude);
        values.put(FacilityDB.COLUMN_BACKID, backId);



        // insert row
        long id = db.insert(FacilityDB.TABLE_NAME, null, values);

        // close db connection
        db.close();

        // return newly inserted row id
        return id;
    }

    public List<FacilityDB>  getAllFacilities() {
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FacilityDB.TABLE_NAME,
                null,
                null, null, null, null, null);



        List facilityDBList = new ArrayList<>();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                // prepare UserDB object
                FacilityDB facilityDB = new FacilityDB(
                        cursor.getInt(cursor.getColumnIndex(FacilityDB.COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_NAME)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_ADDRESS)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_LOCATION)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_LATITUDE)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_LONGITUDE)),
                        cursor.getString(cursor.getColumnIndex(FacilityDB.COLUMN_BACKID))
                );
                cursor.moveToNext();
                facilityDBList.add(facilityDB);
            }

        }

        // close the db connection
        cursor.close();

        return facilityDBList;
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

    public UserDB updateUser(User user, String username, String city_new_value) {
        SQLiteDatabase db = this.getWritableDatabase();
        UserDB userDB = this.getUserByUsername(username);
        ContentValues values = new ContentValues();

        if (userDB != null) {
            values.put(UserDB.COLUMN_FIRSTNAME, user.getFirstName());
            values.put(UserDB.COLUMN_LASTNAME, user.getLastName());
            values.put(UserDB.COLUMN_ADDRESS, user.getAddress());
            values.put(UserDB.COLUMN_LOCATION, city_new_value);
        }

        // Which row to update, based on the title
        String selection = UserDB.COLUMN_USERNAME + " LIKE ?";
        String[] selectionArgs = {username};

        int count = db.update(
                UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        
        return userDB;
    }

    public UserDB updatePreferences(String username, Integer distance, String facilityType, Boolean notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        UserDB userDB = this.getUserByUsername(username);
        ContentValues values = new ContentValues();

        if (userDB != null) {
            values.put(UserDB.COLUMN_FIRSTNAME, userDB.getFirstName());
            values.put(UserDB.COLUMN_LASTNAME, userDB.getLastName());
            values.put(UserDB.COLUMN_ADDRESS, userDB.getAddress());
            values.put(UserDB.COLUMN_LOCATION, userDB.getLocation());
            values.put(UserDB.COLUMN_USERNAME, username);

        }
        if(distance != null) {
            values.put(UserDB.COLUMN_MAXDISTANCE, distance);
        }else {
            values.put(UserDB.COLUMN_MAXDISTANCE, userDB.getMaxDistance());
        }
        if(facilityType != null ){
            values.put(UserDB.COLUMN_FACILITYTYPE, facilityType);
        }else {
            values.put(UserDB.COLUMN_FACILITYTYPE, userDB.getFacilityType());
        }
        if(notification != null ) {
            values.put(UserDB.COLUMN_COMMENT_NOTIFICATION, notification);
        }else {
            values.put(UserDB.COLUMN_COMMENT_NOTIFICATION, userDB.isComment_notification());
        }

        // Which row to update, based on the title
        String selection = UserDB.COLUMN_USERNAME + " LIKE ?";
        String[] selectionArgs = {username};

        int count = db.update(
                UserDB.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        return userDB;
    }

}

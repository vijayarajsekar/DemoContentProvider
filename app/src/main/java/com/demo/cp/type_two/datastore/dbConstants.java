package com.demo.cp.type_two.datastore;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class dbConstants {

    // Database table
    public static final String TABLE_USER = "userDetails";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_UNAME = "userName";
    public static final String COLUMN_AGE = "Age";

    // Database creation SQL statement
    private static final String DATABASE_CREATE = "create table "
            + TABLE_USER
            + "(" + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_UNAME + " text not null, "
            + COLUMN_AGE
            + " text not null" + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion,
                                 int newVersion) {
        Log.w(dbConstants.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(database);
    }
}

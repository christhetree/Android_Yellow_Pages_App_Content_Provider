package com.tddrampup.databases;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by WX009-PC on 2/26/14.
 */
public class ListingsTable {

    public static final String LISTINGS_TABLE = "listingsTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_LISTING_ID = "listing_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_MERCHANT_URL = "merchant_url";
    public static final String COLUMN_LATITUDE = "latitude";
    public static final String COLUMN_LONGITUDE = "longitude";

    private static final String DATABASE_CREATE = "create table "
            + LISTINGS_TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_LISTING_ID + " text not null, "
            + COLUMN_NAME + " text not null, "
            + COLUMN_STREET + " text not null, "
            + COLUMN_CITY + " text not null, "
            + COLUMN_MERCHANT_URL + " text not null, "
            + COLUMN_LATITUDE + " text not null, "
            + COLUMN_LONGITUDE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ListingsTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + LISTINGS_TABLE);
        onCreate(db);
    }
}

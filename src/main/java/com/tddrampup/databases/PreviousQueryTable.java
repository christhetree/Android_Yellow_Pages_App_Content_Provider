package com.tddrampup.databases;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by: WX009-PC
 * on: 2/27/14.
 */
public class PreviousQueryTable {

    public static final String PREVIOUS_QUERY_TABLE = "previousQueryTable";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_WHAT = "whatQuery";
    public static final String COLUMN_WHERE = "whereQuery";

    private static final String DATABASE_CREATE = "create table "
            + PREVIOUS_QUERY_TABLE
            + "("
            + COLUMN_ID + " integer primary key autoincrement, "
            + COLUMN_WHAT + " text not null, "
            + COLUMN_WHERE + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(ListingsTable.class.getName(), "Upgrading database from version " + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + PREVIOUS_QUERY_TABLE);
        onCreate(db);
    }
}

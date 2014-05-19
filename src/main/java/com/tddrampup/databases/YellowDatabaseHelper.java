package com.tddrampup.databases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by WX009-PC on 2/27/14.
 */
public class YellowDatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "yellow.db";
    private static final int DATABASE_VERSION = 1;

    public YellowDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        PreviousQueryTable.onCreate(db);
        ListingsTable.onCreate(db);
        SearchTable.onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        PreviousQueryTable.onUpgrade(db, oldVersion, newVersion);
        ListingsTable.onUpgrade(db, oldVersion, newVersion);
        SearchTable.onUpgrade(db, oldVersion, newVersion);
    }
}


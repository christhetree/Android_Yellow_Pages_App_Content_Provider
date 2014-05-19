package com.tddrampup.databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.tddrampup.contentProviders.YellowContentProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by WX009-PC on 2/27/14.
 */
public class PreviousQueryTableHelper {

    public static void setQuery(Context context, final String what, final String where) {
        Cursor cursor = context.getContentResolver().query(YellowContentProvider.CONTENT_URI_QUERY, null, null, null, null);
        if (!cursor.moveToFirst()) {
            addQuery(context, what, where);
        } else {
            updateQuery(context, what, where);
        }
    }

    private static void addQuery(Context context, final String what, final String where) {
        ContentValues values = new ContentValues();
        values.put(PreviousQueryTable.COLUMN_WHAT, what);
        values.put(PreviousQueryTable.COLUMN_WHERE, where);
        context.getContentResolver().insert(YellowContentProvider.CONTENT_URI_QUERY, values);
    }

    private static void updateQuery(Context context, final String what, final String where) {
        ContentValues values = new ContentValues();
        values.put(PreviousQueryTable.COLUMN_WHAT, what);
        values.put(PreviousQueryTable.COLUMN_WHERE, where);
        context.getContentResolver().update(YellowContentProvider.CONTENT_URI_QUERY, values, null, null);
    }

    public static List<String> getQuery(Context context) {
        Cursor cursor = context.getContentResolver().query(YellowContentProvider.CONTENT_URI_QUERY, null, null, null, null);
        int whatIndex = cursor.getColumnIndex(PreviousQueryTable.COLUMN_WHAT);
        int whereIndex = cursor.getColumnIndex(PreviousQueryTable.COLUMN_WHERE);

        ArrayList<String> query = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            query.add(cursor.getString(whatIndex));
            query.add(cursor.getString(whereIndex));
        } else {
            query.add("");
            query.add("");
        }
        return query;
    }
}

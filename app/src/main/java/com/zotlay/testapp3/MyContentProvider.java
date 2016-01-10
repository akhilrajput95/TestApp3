package com.zotlay.testapp3;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Akhil Rajput on 1/8/2016.
 */
public class MyContentProvider extends ContentProvider {

    private MyDatabase mHelper;
    private SQLiteDatabase db;



    @Override
    public boolean onCreate() {
        mHelper = new MyDatabase(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = mHelper.getWritableDatabase();
        long rowID = db.insert(MyDataBaseStructure.FeedEntry.TABLE_NAME, "", values);
        if (rowID > 0) {
            Uri _uri = ContentUris.withAppendedId(uri, rowID);
            return _uri;
        }

       return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}

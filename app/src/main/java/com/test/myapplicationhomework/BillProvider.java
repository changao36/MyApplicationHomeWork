package com.test.myapplicationhomework;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BillProvider extends ContentProvider {

    private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
    private static String authority = "com.test.myapplicationhomework.billProvider";
    private static final int USER_CODE = 1;
    private DBManager myDataBase;
    private SQLiteDatabase db;


    static {
        MATCHER.addURI(authority, null, USER_CODE);
    }

    @Override
    public boolean onCreate() {
        myDataBase = new DBManager(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] strings, @Nullable String s, @Nullable String[] strings1, @Nullable String s1) {
        db = myDataBase.getWritableDatabase();
        return db.query("billing", new String[]{"time", "money", "flag"}, null, null, null, null, s1);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        db = myDataBase.getWritableDatabase();
        db.insert("billing", null, contentValues);
        db.close();
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        return 0;
    }
}

package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class AppInstaller extends ContentProvider {

    /**
     * 通过ContentProvider获取ApplicationContext
     */
    private static Context context;

    public static Context getApplicationContext() {
        return context;
    }

    @Override
    public boolean onCreate() {
        //ContentProvider中获取到的getContext就是Application的getApplicationContext，并且ContentProvider先于Application初始化
        context = getContext();
        Log.d("mtest", "AppProvider onCreate  "+context);
        SingleManager.getInstance(context).init();
        return false;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}

package com.example.carmarket;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class myDatabase extends SQLiteAssetHelper {

    public static final String DB_NAME="cars.db";
    public static final String TABLE_NAME="car";
    public static final int DB_VERSION=1;

    public static final String column_id="id";
    public static final String column_model="model";
    public static final String column_color="color";
    public static final String column_image="image";
    public static final String column_description="description";
    public static final String column_distancePerLetter="dpl";
    public static final String column_phone="phone";
    public myDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
}

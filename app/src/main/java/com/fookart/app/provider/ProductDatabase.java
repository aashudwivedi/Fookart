package com.fookart.app.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import static com.fookart.app.provider.ProductContract.ProductColumns;
import static com.fookart.app.util.LogUtils.makeLogTag;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductDatabase extends SQLiteOpenHelper{
    final String TAG = makeLogTag(ProductDatabase.class);

    public static final String DATABASE_NAME = "fookart.db";

    public static final int DATABASE_VERSION = 1;

    public final Context mContext;

    public ProductDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;
    }

    interface Tables {
        String PRODUCTS = "products";
    }

    @Override
    public  void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + Tables.PRODUCTS + " ("
                + BaseColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + ProductColumns.PRODUCT_ID + " INTEGER NOT NULL,"
                + ProductColumns.NAME + " STRING NOT NULL,"
                + ProductColumns.DETAILS + " STRING NOT NULL,"
                + ProductColumns.CATEGORY + " STRING NOT NULL,"
                + ProductColumns.IMAGE_URL + " STRING NOT NULL,"
                + ProductColumns.PRICE + " REAL NOT NULL );"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}

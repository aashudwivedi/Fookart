package com.fookart.app.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import java.util.Arrays;

import static com.fookart.app.util.LogUtils.LOGV;
import static com.fookart.app.util.LogUtils.makeLogTag;
import static com.fookart.app.provider.ProductDatabase.Tables;
import static com.fookart.app.provider.ProductContract.Products;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductProvider extends ContentProvider {
    private static final String TAG = makeLogTag(ProductProvider.class);

    private ProductDatabase mOpenHelper;

    private static final UriMatcher sUriMatcher = buildUriMatcher();

    private static final int PRODUCTS = 100;
    private static final int PRODUCTS_ID = 101;

    private static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        matcher.addURI(ProductContract.CONTENT_AUTHOURTY, "products", PRODUCTS);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mOpenHelper = new ProductDatabase(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        LOGV(TAG, "query(uri=" + uri + ", proj=" + Arrays.toString(projection) + ")");
        final SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                qb.setTables(Tables.PRODUCTS);
                break;
            case PRODUCTS_ID:
                qb.setTables(Tables.PRODUCTS);
                //TODO: FIX THIS
                //qb.query()
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        final SQLiteDatabase db = mOpenHelper.getReadableDatabase();
        return qb.query(db, null, null, null, null, null, null);

    }

    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                return ProductContract.Products.CONTENT_TYPE;
            case PRODUCTS_ID:
                return ProductContract.Products.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        LOGV(TAG, "insert(uri=" + uri + ", values=" + values.toString() + ")");
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case PRODUCTS:
                db.insertOrThrow(Tables.PRODUCTS, null, values);
                // notify change required ??
                return Products.buildProductUri(values.getAsString(Products.PRODUCT_ID));
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
}

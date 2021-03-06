package com.fookart.app.ui;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.TextView;
import com.fookart.app.R;
import com.fookart.app.model.CartModel;
import com.fookart.app.provider.ProductContract;

import java.util.Set;

import static com.fookart.app.provider.ProductContract.ProductColumns.*;

/**
 * Created by ashu on 30/7/14.
 */
@SuppressLint("NewApi")
public class CartActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>,
        CartModel.CartCallBack{

    public static final int LOADER_ID = 102;

    public boolean mPaused;

    public boolean mOnResumeNeedsLoad = false;

    static String [] sProjection = new String []{
            NAME,
            PRICE,
            PRODUCT_ID,
            BaseColumns._ID,
    };

    public SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        CartModel.getInstance().setCallback(this);

        int[] viewIds = {R.id.product_name, R.id.price, R.id.count};
        mAdapter = new SimpleCursorAdapter(
                this,
                R.layout.cart_list_item,
                null,
                sProjection,
                viewIds,
                0);

        mAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
            @Override
            public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
                if(columnIndex == 2) {
                    int idx = cursor.getColumnIndex(PRODUCT_ID);
                    String id = cursor.getString(idx);
                    int count = CartModel.getInstance().getCountForId(id);
                    ((TextView)(view)).setText(String.valueOf(count));
                    return true;
                }
                return false;
            }
        });

        getLoaderManager().initLoader(LOADER_ID, null, this);
        setListAdapter(mAdapter);


    }

    @Override
    protected void onPause() {
        super.onPause();
        mPaused = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPaused = false;
        if(mOnResumeNeedsLoad) {
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Set<String> ids = CartModel.getInstance().getItems().keySet();
        String selection = PRODUCT_ID + " IN (" + getParamsQuery(ids.size()) + ")";
        CursorLoader loader = new CursorLoader(
                this,
                ProductContract.Products.CONTENT_URI,
                sProjection,
                selection,
                ids.toArray(new String[ids.size()]),
                null);
        return loader;
    }

    String getParamsQuery(int count) {
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < count; i++) {
            builder.append("?");
            if(i != count - 1) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    @Override
    public void updateCart() {
        if(mPaused) {
            mOnResumeNeedsLoad = true;
        } else {
            getLoaderManager().restartLoader(LOADER_ID, null, this);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String text = "count = " + String.valueOf(data.getCount());
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

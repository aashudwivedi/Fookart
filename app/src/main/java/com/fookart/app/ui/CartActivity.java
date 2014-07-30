package com.fookart.app.ui;

import android.app.ListActivity;
import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.widget.Toast;
import com.fookart.app.R;
import com.fookart.app.provider.ProductContract;
import com.fookart.app.util.CartUtils;

import java.util.Set;

import static com.fookart.app.provider.ProductContract.ProductColumns.*;

/**
 * Created by ashu on 30/7/14.
 */
public class CartActivity extends ListActivity
        implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final int LOADER_ID = 102;

    static String [] sProjection = new String []{
            BaseColumns._ID,
            NAME,
            PRICE,
    };

    public CursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);

        int[] viewIds = {R.id.product_name};
        mAdapter = new SimpleCursorAdapter(
                this,
                R.layout.list_item,
                null,
                sProjection,
                viewIds,
                0);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        setListAdapter(mAdapter);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Set<String> ids = CartUtils.getItems(this).keySet();
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
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        String text = "count = " + String.valueOf(data.getCount());
        Toast.makeText(getApplicationContext(), text, 100).show();
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

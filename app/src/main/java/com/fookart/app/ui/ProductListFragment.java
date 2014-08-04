package com.fookart.app.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.widget.ListView;
import com.fookart.app.R;

import java.lang.ref.WeakReference;

import static com.fookart.app.provider.ProductContract.ProductColumns;
import static com.fookart.app.provider.ProductContract.Products;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductListFragment extends ListFragment
        implements LoaderManager.LoaderCallbacks<Cursor>{
    public interface Callbacks {
        public void onItemSelected(String id);
    }


    public static final int LOADER_ID = 100;

    SimpleCursorAdapter mAdapter;

    public WeakReference<Callbacks> mCallback;

    static String [] sProjection = new String []{
            ProductColumns.NAME,
            ProductColumns.PRICE,
            BaseColumns._ID,};

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        int[] viewIds = {R.id.product_name, R.id.price};
        mAdapter = new SimpleCursorAdapter(
                getActivity(),
                R.layout.list_item,
                null,
                sProjection,
                viewIds,
                0);

        setListAdapter(mAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);

        mCallback = new WeakReference<Callbacks>((Callbacks)getActivity());
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Callbacks callback = mCallback.get();
        if( callback != null) {
            callback.onItemSelected(String.valueOf(id));
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                this.getActivity(),
                Products.CONTENT_URI,
                sProjection,
                null,
                null,
                null
        );
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.swapCursor(null);
    }
}

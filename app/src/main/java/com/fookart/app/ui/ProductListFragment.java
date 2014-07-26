package com.fookart.app.ui;


import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.SimpleCursorAdapter;
import com.fookart.app.R;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductListFragment extends ListFragment {
    boolean mDualPane;
    private SimpleCursorAdapter mAdapter;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        String[] dataColumns = { "" };
        int[] viewIds = {};
        mAdapter = new SimpleCursorAdapter(getActivity(), R.layout.list_item, null,
                dataColumns, viewIds, 0);
        setListAdapter(mAdapter);
    }
}

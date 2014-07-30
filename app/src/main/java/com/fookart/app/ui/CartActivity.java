package com.fookart.app.ui;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import com.fookart.app.R;
import com.fookart.app.util.CartUtils;

/**
 * Created by ashu on 30/7/14.
 */
public class CartActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart_layout);
    }

    private class CartAdapter extends ListAdapter{

        @Override
        public int getCount() {
            return CartUtils.getItemCount();
        }

        @Override
        public Object getItem(int position) {

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return null;
        }
    }
}

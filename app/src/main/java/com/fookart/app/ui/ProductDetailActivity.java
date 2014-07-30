package com.fookart.app.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.fookart.app.R;

/**
 * Created by ashu on 27/7/14.
 */
public class ProductDetailActivity extends ActionBarActivity{
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.activity_product_detail);

        if(savedInstance == null) {
            String productId = getIntent().getStringExtra(
                    ProductDetailFragment.PRODUCT_ID);
            Bundle bundle = new Bundle();
            bundle.putString(ProductDetailFragment.PRODUCT_ID, productId);

            ProductDetailFragment fragment = new ProductDetailFragment();
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.product_detail_container, fragment).commit();
        }
    }
}

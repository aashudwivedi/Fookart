package com.fookart.app.ui;

import android.os.Bundle;
import com.fookart.app.R;

/**
 * Created by ashu on 27/7/14.
 */
public class ProductDetailActivity extends BaseActivity{
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

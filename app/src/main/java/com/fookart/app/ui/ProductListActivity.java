package com.fookart.app.ui;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import com.fookart.app.R;
import com.fookart.app.io.JSONHandler;
import com.fookart.app.io.ProductsHandler;
import com.fookart.app.provider.ProductContract;

import java.io.IOException;
import java.util.ArrayList;

import static com.fookart.app.provider.ProductContract.CONTENT_AUTHOURTY;
import static com.fookart.app.ui.ProductDetailFragment.PRODUCT_ID;


public class ProductListActivity extends BaseActivity
        implements ProductListFragment.Callbacks{
    private boolean mTWoPaneMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.details) != null) {
            mTWoPaneMode = true;
        }
        addInitialData();
    }

    /**
     * Check if the initial data has already been inserted, if not populate
     * the initial data. This part of the code should ideally be inside the
     * perform sync method, when the app is hooked to android's sync framework
     * and should not be called from UI thread, but this being a demo application
     * and the size of data being really really small, we are calling it from the
     * onCreate method of the activity
     */
    private void addInitialData() {
        final ContentResolver resolver = this.getContentResolver();
        ArrayList<ContentProviderOperation> batch = new ArrayList<ContentProviderOperation>();
        Cursor cursor = null;
        try {

            cursor = resolver.query(ProductContract.Products.CONTENT_URI,
                    new String[]{ ProductContract.Products.NAME },
                    null, null, null);
            if(cursor.getCount() < 1) {
                batch.addAll(new ProductsHandler(this).parse(
                        JSONHandler.parseResource(this, R.raw.products)));
                resolver.applyBatch(CONTENT_AUTHOURTY, batch);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        } finally {
            if(cursor != null) {
                cursor.close();
            }
        }
    }

    @Override
    public void onItemSelected(String  id) {
        if(mTWoPaneMode) {
            Bundle bundle = new Bundle();
            bundle.putString(PRODUCT_ID, id);
            ProductDetailFragment detailFragment = new ProductDetailFragment();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(
                    R.id.details, detailFragment).commit();
        } else {
            Intent detailIntent = new Intent(this, ProductDetailActivity.class);
            detailIntent.putExtra(PRODUCT_ID, id);
            startActivity(detailIntent);
        }
    }
}

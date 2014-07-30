package com.fookart.app.ui;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import com.fookart.app.R;
import com.fookart.app.io.JSONHandler;
import com.fookart.app.io.ProductsHandler;
import com.fookart.app.provider.ProductContract;

import java.io.IOException;
import java.util.ArrayList;

import static com.fookart.app.provider.ProductContract.CONTENT_AUTHOURTY;
import static com.fookart.app.ui.ProductDetailFragment.PRODUCT_ID;


public class ProductListActivity extends FragmentActivity
        implements ProductListFragment.Callbacks{
    private boolean mTWoPaneMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(findViewById(R.id.details) != null) {
            mTWoPaneMode = true;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        if(id == R.id.action_cart) {
            startActivity(new Intent(this, CartActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
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

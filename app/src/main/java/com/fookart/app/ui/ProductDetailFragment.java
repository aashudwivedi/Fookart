package com.fookart.app.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.fookart.app.R;
import com.fookart.app.model.CartModel;

import static com.fookart.app.provider.ProductContract.ProductColumns;
import static com.fookart.app.provider.ProductContract.Products;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductDetailFragment extends Fragment
        implements LoaderManager.LoaderCallbacks<Cursor>{

    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final int LOADER_ID = 101;
    private String mProductId;

    private static String [] sProjection = {
            ProductColumns.NAME,
            ProductColumns.PRICE,
            ProductColumns.CATEGORY,
            ProductColumns.IMAGE_URL,
            ProductColumns.DETAILS,
    };
    private static String sSelection = ProductColumns.PRODUCT_ID + " = ?";

    private TextView mNameText;
    private TextView mDetailsText;
    private TextView mCategoryText;
    private TextView mPriceText;
    private ImageView mProductImage;
    private Button mAddToCartButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mProductId = getArguments().getString(PRODUCT_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.product_detail, container, false);

        mNameText = (TextView)root.findViewById(R.id.name);
        mDetailsText = (TextView)root.findViewById(R.id.details);
        mCategoryText = (TextView)root.findViewById(R.id.category);
        mPriceText = (TextView)root.findViewById(R.id.price);
        mProductImage = (ImageView)root.findViewById(R.id.image);

        mAddToCartButton = (Button)root.findViewById(R.id.add_to_cart);
        final String productId = mProductId;
        mAddToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CartModel.getInstance().addtem( productId);
            }
        });

        getLoaderManager().initLoader(LOADER_ID, null, this);

        return root;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        CursorLoader loader = new CursorLoader(
                getActivity(),
                Products.CONTENT_URI,
                sProjection,
                sSelection,
                new String[] { mProductId },
                null);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // this cursor is supposed to have have just one row
        data.moveToFirst();

        int nameIdx = data.getColumnIndexOrThrow(ProductColumns.NAME);
        int detailidx = data.getColumnIndexOrThrow(ProductColumns.DETAILS);
        int categoryIdx = data.getColumnIndexOrThrow(ProductColumns.CATEGORY);
        int imageIdx = data.getColumnIndexOrThrow(ProductColumns.CATEGORY);
        int priceIdx = data.getColumnIndexOrThrow(ProductColumns.PRICE);

        String name = data.getString(nameIdx);
        String details = data.getString(detailidx);
        String category = data.getString(categoryIdx);
        float price = data.getFloat(priceIdx);
        String imageUrl = data.getString(imageIdx);

        mNameText.setText(name);
        mDetailsText.setText(details);
        mCategoryText.setText(category);
        mPriceText.setText(String.valueOf(price));
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}

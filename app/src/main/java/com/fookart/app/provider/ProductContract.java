package com.fookart.app.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductContract {
    public static final String CONTENT_AUTHOURTY = "com.fookart.app.provider";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHOURTY);

    public static final String PATH_PRODUCTS = "products";

    public interface ProductColumns {
        String PRODUCT_ID = "PRODUCT_ID";
        String NAME = "Name";
        String  PRICE = "PRICE";
        String CATEGORY = "CATEGORY";
        String DETAILS = "DETAILS";
        String IMAGE_URL = "IMAGE_URL";
    }

    public static class Products implements ProductColumns, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PRODUCTS).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.fookart.product";

        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item./vnd.moneydroid.product";

        public static Uri buildProductUri(String productId) {
            return CONTENT_URI.buildUpon().appendPath(String.valueOf(productId)).build();
        }

        public static String getId(Uri uri) { return uri.getPathSegments().get(1);}
    }
}

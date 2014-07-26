package com.fookart.app.io;

import android.content.ContentProviderOperation;
import android.content.Context;
import com.fookart.app.io.model.Product;
import com.fookart.app.io.model.Products;
import com.fookart.app.provider.ProductContract;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.ArrayList;

import static com.fookart.app.util.LogUtils.makeLogTag;

/**
 * Created by ashu on 26/7/14.
 */
public class ProductsHandler extends JSONHandler {
    private static final String TAG = makeLogTag(ProductsHandler.class);

    public ProductsHandler(Context context) {super(context);}

    @Override
    public ArrayList<ContentProviderOperation> parse(String json)
            throws IOException{
        final ArrayList<ContentProviderOperation> batch =
                new ArrayList<ContentProviderOperation>();
        Products productJson = new Gson().fromJson(json, Products.class);
        int noOfProducts = productJson.getProduct().length;
        for(int i = 0; i < noOfProducts; i++) {
            parseProduct(productJson.getProduct()[i], batch);
        }
        return batch;
    }

    public static void parseProduct(Product product, ArrayList<ContentProviderOperation> batch) {
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                ProductContract.Products.CONTENT_URI);
        builder.withValue(ProductContract.Products.PRODUCT_ID, product.id);
        builder.withValue(ProductContract.Products.NAME, product.name);
        builder.withValue(ProductContract.Products.CATEGORY, product.category);
        builder.withValue(ProductContract.Products.DETAILS, product.details);
        builder.withValue(ProductContract.Products.IMAGE_URL, product.imageUrl);
        builder.withValue(ProductContract.Products.PRICE, product.price);
        batch.add(builder.build());
    }
}

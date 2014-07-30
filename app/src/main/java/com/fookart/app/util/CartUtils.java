package com.fookart.app.util;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

import static com.fookart.app.util.LogUtils.makeLogTag;

/**
 * Created by ashu on 30/7/14.
 */
public class CartUtils {
    public static final String TAG = makeLogTag(CartUtils.class);

    public static final String PREF_NAME = "cart_pref";

    public static void addtem(final Context context, String id) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        HashMap<String, Integer> cartItems = (HashMap<String, Integer>) pref.getAll();
        boolean exits = false;
        for(String productId : cartItems.keySet()) {
            if(productId.equals(id)) {
                int count = cartItems.get(productId);
                editor.putInt(id, count + 1);
                exits = true;
                break;
            }
        }
        if(!exits) {
            editor.putInt(id, 1);
        }
        editor.commit();
    }

    public static HashMap<String, Integer> getItems(final Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return (HashMap<String, Integer>) pref.getAll();
    }

    public static int getItemCount(final Context context) {
        return getItems(context).size();
    }
}

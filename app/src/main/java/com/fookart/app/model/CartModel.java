package com.fookart.app.model;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.ref.WeakReference;
import java.util.HashMap;

/**
 * Created by ashu on 3/8/14.
 */
public class CartModel {
    public interface CartCallBack {
        public void updateCart();
    }

    public static final String PREF_NAME = "cart_pref";

    private static CartModel sInstance;

    public boolean mRefreshReq = false;

    public FooCartApplication mApp;

    private WeakReference<CartCallBack> mCartCallbacks;

    public static synchronized CartModel getInstance() {
        if(sInstance == null) {
            sInstance = new CartModel();
        }
        return sInstance;
    }

    public void setApp(FooCartApplication app) {
        mApp = app;
    }

    public void setCallback(CartCallBack c) {
        mCartCallbacks = new WeakReference<CartCallBack>(c);
    }

    public void addtem(String id) {
        SharedPreferences pref = mApp.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        HashMap<String, Integer> cartItems = (HashMap<String, Integer>) pref.getAll();
        boolean exits = false;
        for(String productId : cartItems.keySet()) {
            if(productId.equals(id)) {
                int count = cartItems.get(productId);
                count = count + 1;
                editor.putInt(id, count);
                exits = true;
                break;
            }
        }
        if(!exits) {
            editor.putInt(id, 1);
        }
        editor.commit();
        requestUIUpdate();
    }

    public HashMap<String, Integer> getItems() {
        SharedPreferences pref = mApp.getSharedPreferences(PREF_NAME,
                Context.MODE_PRIVATE);
        return (HashMap<String, Integer>) pref.getAll();
    }

    public int getItemCount() {
        return getItems().size();
    }

    private void requestUIUpdate() {
        if(mCartCallbacks != null ) {
            CartCallBack c = mCartCallbacks.get();
            if (c != null) {
                c.updateCart();
            }
        }
    }
}

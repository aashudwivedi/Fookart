package com.fookart.app.model;

import android.app.Application;
import com.fookart.app.model.CartModel;

/**
 * Created by ashu on 3/8/14.
 */
public class FooCartApplication extends Application {
    public CartModel mCartModel;

    @Override
    public void onCreate() {
        super.onCreate();
        mCartModel = CartModel.getInstance();
        mCartModel.setApp(this);
    }

    public CartModel getCartModel() {
        return mCartModel;
    }
}

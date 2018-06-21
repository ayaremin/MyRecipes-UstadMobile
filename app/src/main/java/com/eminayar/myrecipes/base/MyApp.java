package com.eminayar.myrecipes.base;

import android.app.Application;

import io.realm.Realm;

/**
 * Created by EminAyar on 21.06.2018.
 */

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //Inıt realm
        Realm.init(this);
    }
}

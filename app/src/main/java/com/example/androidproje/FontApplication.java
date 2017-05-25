package com.example.androidproje;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by alperbeyler on 23/03/16.
 */
public class FontApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("JustBreatheBdObl7.otf")  // default font
                .setFontAttrId(R.attr.fontPath)
                .build()

        );

    }




}
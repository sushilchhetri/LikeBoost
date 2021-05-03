package com.lynhillsoftwares.likeboost;

import android.app.Application;

import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.paperdb.Paper;

/**
 * Created by Sushil Chhetri on 03,May,2021
 */
public class App extends Application {
    private static App instance;

    public App(){

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initApplication();
        set_Calligraphy();
    }

    /**
     *TODO set font calligraphy
     */
    private void set_Calligraphy() {


        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()

                                .setDefaultFontPath("fonts/MainRg.otf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build());

        Paper.init(this);
    }


    /*TODO init Application class*/
    private void initApplication() {
        instance = this;
    }


    /*TODO get instance of application class*/
    public static App getInstance(){
        return instance;
    }

}

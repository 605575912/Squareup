package com.squareup.code;

//import dagger.Module;
//import dagger.Provides;

/**
 * Created by Administrator on 2017/08/09 0009.
 */

//@Module
public class ActivityModule {

    public ActivityModule() {
    }

//    @Provides
//    public DaggerActivity provideActivity() {
//        return activity;
//    }
//
//    @Provides
//    public User provideUser() {
//        return new User("user form ActivityModule");
//    }

//    @Provides
    public LauncherPenster provideDaggerPresenter() {
        return new LauncherPenster();
    }
}

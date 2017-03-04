package deint.jvmed.clientmanagement.others;

import android.app.Application;
import android.content.Context;

import deint.jvmed.clientmanagement.database.DatabaseHelper;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        DatabaseHelper.getInstance().open();
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}

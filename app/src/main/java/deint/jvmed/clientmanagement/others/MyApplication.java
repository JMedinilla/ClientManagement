package deint.jvmed.clientmanagement.others;

import android.app.Application;
import android.content.Context;
import android.content.Intent;

import deint.jvmed.clientmanagement.database.DatabaseHelper;
import deint.jvmed.clientmanagement.services.Service_MeetingCheck;

public class MyApplication extends Application {
    private static MyApplication instance;

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
        DatabaseHelper.getInstance().open();
        startService(new Intent(instance.getApplicationContext(), Service_MeetingCheck.class));
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}

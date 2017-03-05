package deint.jvmed.clientmanagement.services;

import android.app.IntentService;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;

import deint.jvmed.clientmanagement.database.DatabaseContract;
import deint.jvmed.clientmanagement.database.DatabaseHelper;
import deint.jvmed.clientmanagement.receivers.TodayMeetings;

public class Service_MeetingCheck extends IntentService {

    public Service_MeetingCheck() {
        super("MeetingCheck");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                int rows;
                SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
                Cursor cursor = sqLiteDatabase.query(DatabaseContract.MeetingTable.TABLE_NAME,
                        DatabaseContract.MeetingTable.ALL_COLUMNS, DatabaseContract.MeetingTable.COLUMN_DATE + " = date('now')",
                        null, null, null, null);

                rows = cursor.getCount();

                cursor.close();
                DatabaseHelper.getInstance().closeDatabase();

                Intent intentBroad = new Intent(TodayMeetings.ACTION_TODAYMEETINGS);
                if (rows > 0) {
                    intentBroad.putExtra("there_are", true);
                } else {
                    intentBroad.putExtra("there_are", false);
                }
                sendBroadcast(intentBroad);

                handler.postDelayed(this, 1800000);
            }
        }, 2000);

        return START_STICKY;
    }
}

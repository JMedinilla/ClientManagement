package deint.jvmed.clientmanagement.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;

import deint.jvmed.clientmanagement.Activity_Home;
import deint.jvmed.clientmanagement.R;

public class InsertReceiver extends BroadcastReceiver {
    public static final String ACTION_INSERT = "deint.jvmed.clientmanagement_INSERT";
    public static final int OPEN_LIST = 10;
    public static final int OPEN_FRAGMENT_FROM_NOTIFICATION = 20;

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, Activity_Home.class);
        newIntent.putExtra("openFromNotification", OPEN_FRAGMENT_FROM_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, OPEN_LIST, newIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setContentTitle(context.getString(R.string.insertNotification_title));
        builder.setContentText(context.getString(R.string.insertNotification_description));
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        builder.setContentIntent(pendingIntent);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(OPEN_LIST, builder.build());
    }
}

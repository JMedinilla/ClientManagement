package deint.jvmed.clientmanagement.receivers;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import deint.jvmed.clientmanagement.Activity_Home;
import deint.jvmed.clientmanagement.R;

public class TodayMeetings extends BroadcastReceiver {
    public static final String ACTION_TODAYMEETINGS = "deint.jvmed.clientmanagement_TODAYMEETINGS";
    public static final int OPEN_LIST = 30;
    public static final int OPEN_FRAGMENT_FROM_NOTIFICATION = 40;

    public TodayMeetings() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent newIntent = new Intent(context, Activity_Home.class);
        newIntent.putExtra("openFromNotification", OPEN_FRAGMENT_FROM_NOTIFICATION);

        PendingIntent pendingIntent = PendingIntent.getActivity(context, OPEN_LIST, newIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        boolean res = intent.getExtras().getBoolean("there_are");
        if (res) {
            builder.setContentTitle(context.getString(R.string.meetingNotification_title));
            builder.setContentText(context.getString(R.string.meetingNotification_desc));
        } else {
            builder.setContentTitle(context.getString(R.string.meetingNotification_title_no));
            builder.setContentText(context.getString(R.string.meetingNotification_desc));
        }
        builder.setSmallIcon(android.R.mipmap.sym_def_app_icon);
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(OPEN_LIST, builder.build());
    }
}

package deint.jvmed.clientmanagement.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class TodayMeetings extends BroadcastReceiver {
    public static final String ACTION_TODAYMEETINGS = "deint.jvmed.clientmanagement_TODAYMEETINGS";

    public TodayMeetings() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

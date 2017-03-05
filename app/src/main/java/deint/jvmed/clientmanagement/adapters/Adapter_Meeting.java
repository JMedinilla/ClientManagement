package deint.jvmed.clientmanagement.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.pojo.Meeting;

public class Adapter_Meeting extends ArrayAdapter<Meeting> {
    private Context context;

    public Adapter_Meeting(Context context) {
        super(context, R.layout.adapter_meeting);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        MeetingHolder meetingHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_meeting, parent, false);
            meetingHolder = new MeetingHolder();
            meetingHolder.name = (TextView) view.findViewById(R.id.adapterMeeting_name);
            meetingHolder.date = (TextView) view.findViewById(R.id.adapterMeeting_date);
            view.setTag(meetingHolder);
        } else {
            meetingHolder = (MeetingHolder) view.getTag();
        }

        Meeting meeting = getItem(position);
        if (meeting != null) {
            meetingHolder.name.setText(meeting.getClient().getName() + " " + meeting.getClient().getSurname());
            meetingHolder.date.setText(meeting.getDate());
        }

        return view;
    }

    @Nullable
    @Override
    public Meeting getItem(int position) {
        return super.getItem(position);
    }

    public void updateList(List<Meeting> meetingList) {
        clear();
        if (meetingList != null) {
            if (meetingList.size() > 0) {
                addAll(meetingList);
            }
        }
        notifyDataSetChanged();
    }

    private class MeetingHolder {
        TextView name;
        TextView date;
    }
}

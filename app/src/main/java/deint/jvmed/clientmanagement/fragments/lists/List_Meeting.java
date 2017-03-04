package deint.jvmed.clientmanagement.fragments.lists;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.adapters.Adapter_Meeting;
import deint.jvmed.clientmanagement.interfaces.PresenterMeeting;
import deint.jvmed.clientmanagement.pojo.Meeting;
import deint.jvmed.clientmanagement.presenters.PresenterMeetingImpl;

public class List_Meeting extends Fragment implements PresenterMeeting.View {
    private fragmentListMeetingInterface callback;
    boolean update;

    ListView listView;
    FloatingActionButton actionButton;

    PresenterMeetingImpl presenterMeeting;
    Adapter_Meeting adapterMeeting;

    @Override
    public void viewSelectAllResponse(List<Meeting> meetingList) {
        adapterMeeting.updateList(meetingList);
    }

    @Override
    public void viewSelectOneResponse(Meeting meeting) {
        if (update) {
            callback.openFormMeetingFragment(meeting);
            update = false;
        } else {

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_meeting, null);
            TextView start = (TextView) view.findViewById(R.id.dialogMeeting_start);
            start.setText(meeting.getStart());
            TextView end = (TextView) view.findViewById(R.id.dialogMeeting_end);
            end.setText(meeting.getEnd());
            TextView description = (TextView) view.findViewById(R.id.dialogMeeting_description);
            description.setText(meeting.getDescription());
            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void viewInsertResponse(long rsult) {
        if (rsult != -1) {
            Toast.makeText(getContext(), R.string.insertsuccess, Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), R.string.inserterror, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void viewUpdateResponse(int result) {
        if (result != 0) {
            Toast.makeText(getContext(), R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), R.string.updateerror, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void viewDeleteResponse(int result) {
        if (result != 0) {
            Toast.makeText(getContext(), R.string.deletesuccess, Toast.LENGTH_SHORT).show();

            presenterMeeting.implSelectAll();
        } else {
            Toast.makeText(getContext(), R.string.deleteerror, Toast.LENGTH_SHORT).show();
        }
    }

    public void fromHomeToList(Meeting meeting, boolean update) {
        if (update) {
            presenterMeeting.implUpdate(meeting);
        } else {
            presenterMeeting.implInsert(meeting);
        }
    }

    public interface fragmentListMeetingInterface {
        void openFormMeetingFragment(Meeting meeting);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        update = false;

        presenterMeeting = new PresenterMeetingImpl(this);
        adapterMeeting = new Adapter_Meeting(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        listView = (ListView) view.findViewById(R.id.meeting_list);
        actionButton = (FloatingActionButton) view.findViewById(R.id.meeting_btnForm);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openFormMeetingFragment(null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Meeting meeting = adapterMeeting.getItem(i);
                if (meeting != null) {
                    presenterMeeting.implSelectOne(meeting.getId());
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapterMeeting);
        registerForContextMenu(listView);

        presenterMeeting.implSelectAll();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (fragmentListMeetingInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Meeting meeting = adapterMeeting.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.contextUpdate:
                callback.openFormMeetingFragment(meeting);
                break;
            case R.id.contextDelete:
                presenterMeeting.implDelete(meeting);
                break;
        }
        return super.onContextItemSelected(item);
    }
}

package deint.jvmed.clientmanagement.presenters;

import android.os.AsyncTask;

import java.util.List;

import deint.jvmed.clientmanagement.database.ManageMeeting;
import deint.jvmed.clientmanagement.interfaces.PresenterMeeting;
import deint.jvmed.clientmanagement.pojo.Meeting;

public class PresenterMeetingImpl implements PresenterMeeting {
    private PresenterMeeting.View view;

    public PresenterMeetingImpl(PresenterMeeting.View view) {
        this.view = view;
    }

    @Override
    public void implSelectAll() {
        new AsyncTask<Void, Void, List<Meeting>>() {
            @Override
            protected List<Meeting> doInBackground(Void... voids) {
                return ManageMeeting.getInstance().selectAll();
            }

            @Override
            protected void onPostExecute(List<Meeting> meetingList) {
                super.onPostExecute(meetingList);
                view.viewSelectAllResponse(meetingList);
            }
        }.execute();
    }

    @Override
    public void implSelectOne(final int id) {
        new AsyncTask<Void, Void, Meeting>() {
            @Override
            protected Meeting doInBackground(Void... voids) {
                return ManageMeeting.getInstance().selectOne(id);
            }

            @Override
            protected void onPostExecute(Meeting meeting) {
                super.onPostExecute(meeting);
                view.viewSelectOneResponse(meeting);
            }
        }.execute();
    }

    @Override
    public void implInsert(final Meeting meeting) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return ManageMeeting.getInstance().insert(meeting);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                view.viewInsertResponse(aLong);
            }
        }.execute();
    }

    @Override
    public void implUpdate(final Meeting meeting) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return ManageMeeting.getInstance().update(meeting);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.viewUpdateResponse(integer);
            }
        }.execute();
    }

    @Override
    public void implDelete(final Meeting meeting) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return ManageMeeting.getInstance().delete(meeting);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.viewDeleteResponse(integer);
            }
        }.execute();
    }
}

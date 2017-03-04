package deint.jvmed.clientmanagement.interfaces;

import java.util.List;

import deint.jvmed.clientmanagement.pojo.Meeting;

public interface PresenterMeeting {
    void implSelectAll();

    void implSelectOne(int id);

    void implInsert(Meeting meeting);

    void implUpdate(Meeting meeting);

    void implDelete(Meeting meeting);

    interface View {
        void viewSelectAllResponse(List<Meeting> meetingList);

        void viewSelectOneResponse(Meeting meeting);

        void viewInsertResponse(long rsult);

        void viewUpdateResponse(int result);

        void viewDeleteResponse(int result);
    }
}

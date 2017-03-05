package deint.jvmed.clientmanagement.interfaces;

import java.util.List;

import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.pojo.Meeting;
import deint.jvmed.clientmanagement.pojo.SpinnerClient;

public interface ValidationPresenter {
    int ERROR_CLIENT_NAME_EMPTY = 100;
    int ERROR_CLIENT_NAME_SHORT = 101;
    int ERROR_CLIENT_NAME_LONG = 102;
    int ERROR_CLIENT_SURNAME_EMPTY = 110;
    int ERROR_CLIENT_SURNAME_SHORT = 111;
    int ERROR_CLIENT_SURNAME_LONG = 112;
    int ERROR_CLIENT_LOCATION_EMPTY = 120;
    int ERROR_CLIENT_NUMBER_EMPTY = 130;
    int ERROR_CLIENT_NUMBER_SHORT = 131;
    int ERROR_CLIENT_NUMBER_LONG = 132;
    int VALID_CLIENT = 140;

    int ERROR_MEETING_DATE_FORMAT = 200;
    int ERROR_MEETING_HOUR_START_FORMAT = 210;
    int ERROR_MEETING_HOUR_END_FORMAT = 211;
    int ERROR_MEETING_HOUR_END_BEFORE = 212;
    int ERROR_MEETING_DESCRIPTION_EMPTY = 220;
    int ERROR_MEETING_DESCRIPTION_SHORT = 221;
    int ERROR_MEETING_DESCRIPTION_LONG = 222;
    int VALID_MEETING = 230;

    void validateClient(Client client);

    void validateMeeting(Meeting meeting);

    void implSelectSpinnerClients();

    interface ViewClient {
        void validationResponse(int result, Client pCl);
    }

    interface ViewMeeting {
        void validationResponse(int result, Meeting pMe);

        void viewSelectSpinnerClientsResponse(List<SpinnerClient> spinnerClients);
    }
}

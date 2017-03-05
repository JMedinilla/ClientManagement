package deint.jvmed.clientmanagement.presenters;

import android.os.AsyncTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import deint.jvmed.clientmanagement.database.ManageClient;
import deint.jvmed.clientmanagement.interfaces.ValidationPresenter;
import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.pojo.Meeting;
import deint.jvmed.clientmanagement.pojo.SpinnerClient;

public class ValidationPresenterImpl implements ValidationPresenter {
    private ValidationPresenter.ViewClient viewClient;
    private ValidationPresenter.ViewMeeting viewMeeting;

    public ValidationPresenterImpl(ValidationPresenter.ViewClient client) {
        this.viewClient = client;
    }

    public ValidationPresenterImpl(ValidationPresenter.ViewMeeting meeting) {
        this.viewMeeting = meeting;
    }

    @Override
    public void validateClient(Client client) {
        if (Objects.equals(client.getName(), "")) {
            viewClient.validationResponse(ERROR_CLIENT_NAME_EMPTY, client);
        } else if (client.getName().length() < 3) {
            viewClient.validationResponse(ERROR_CLIENT_NAME_SHORT, client);
        } else if (client.getName().length() > 20) {
            viewClient.validationResponse(ERROR_CLIENT_NAME_LONG, client);
        } else {
            if (Objects.equals(client.getSurname(), "")) {
                viewClient.validationResponse(ERROR_CLIENT_SURNAME_EMPTY, client);
            } else if (client.getSurname().length() < 3) {
                viewClient.validationResponse(ERROR_CLIENT_SURNAME_SHORT, client);
            } else if (client.getSurname().length() > 40) {
                viewClient.validationResponse(ERROR_CLIENT_SURNAME_LONG, client);
            } else {
                if (Objects.equals(client.getLocation(), "")) {
                    viewClient.validationResponse(ERROR_CLIENT_LOCATION_EMPTY, client);
                } else {
                    if (Objects.equals(client.getNumber(), "")) {
                        viewClient.validationResponse(ERROR_CLIENT_NUMBER_EMPTY, client);
                    } else if (client.getNumber().length() < 9) {
                        viewClient.validationResponse(ERROR_CLIENT_NUMBER_SHORT, client);
                    } else if (client.getNumber().length() > 9) {
                        viewClient.validationResponse(ERROR_CLIENT_NUMBER_LONG, client);
                    } else {
                        viewClient.validationResponse(ValidationPresenter.VALID_CLIENT, client);
                    }
                }
            }
        }
    }

    @Override
    public void validateMeeting(Meeting meeting) {
        if (!isValidDate(meeting.getDate())) {
            viewMeeting.validationResponse(ERROR_MEETING_DATE_FORMAT, meeting);
        } else {
            if (!isValidHour(meeting.getStart())) {
                viewMeeting.validationResponse(ERROR_MEETING_HOUR_START_FORMAT, meeting);
            } else {
                if (!isValidHour(meeting.getEnd())) {
                    viewMeeting.validationResponse(ERROR_MEETING_HOUR_END_FORMAT, meeting);
                } else {
                    if (!firstIsBefore(meeting.getStart(), meeting.getEnd())) {
                        viewMeeting.validationResponse(ERROR_MEETING_HOUR_END_BEFORE, meeting);
                    } else {
                        if (Objects.equals(meeting.getDescription(), "")) {
                            viewMeeting.validationResponse(ERROR_MEETING_DESCRIPTION_EMPTY, meeting);
                        } else if (meeting.getDescription().length() < 6) {
                            viewMeeting.validationResponse(ERROR_MEETING_DESCRIPTION_SHORT, meeting);
                        } else if (meeting.getDate().length() > 200) {
                            viewMeeting.validationResponse(ERROR_MEETING_DESCRIPTION_LONG, meeting);
                        } else {
                            viewMeeting.validationResponse(ValidationPresenter.VALID_MEETING, meeting);
                        }
                    }
                }
            }
        }
    }

    private boolean isValidDate(String s) {
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        format.setLenient(false);
        try {
            format.parse(s.trim());
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

    private boolean isValidHour(String s) {
        boolean result = true;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        format.setLenient(false);
        try {
            format.parse(s.trim());
        } catch (ParseException e) {
            result = false;
        }
        return result;
    }

    private boolean firstIsBefore(String start, String end) {
        boolean result = false;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.getDefault());
        format.setLenient(false);
        try {
            Date startDate = format.parse(start);
            Date endDate = format.parse(end);
            if (startDate.before(endDate)) {
                result = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void implSelectSpinnerClients() {
        new AsyncTask<Void, Void, List<SpinnerClient>>() {
            @Override
            protected List<SpinnerClient> doInBackground(Void... voids) {
                return ManageClient.getInstance().selectAllSpinner();
            }

            @Override
            protected void onPostExecute(List<SpinnerClient> spinnerClients) {
                super.onPostExecute(spinnerClients);
                viewMeeting.viewSelectSpinnerClientsResponse(spinnerClients);
            }
        }.execute();
    }
}

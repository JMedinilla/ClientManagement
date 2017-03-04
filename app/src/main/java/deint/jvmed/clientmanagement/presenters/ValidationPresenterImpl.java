package deint.jvmed.clientmanagement.presenters;

import android.os.AsyncTask;

import java.util.List;

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

    }

    @Override
    public void validateMeeting(Meeting meeting) {

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

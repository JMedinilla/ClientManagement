package deint.jvmed.clientmanagement.presenters;

import android.os.AsyncTask;

import java.util.List;

import deint.jvmed.clientmanagement.database.ManageClient;
import deint.jvmed.clientmanagement.interfaces.PresenterClient;
import deint.jvmed.clientmanagement.pojo.Client;

public class PresenterClientImpl implements PresenterClient {
    private PresenterClient.View view;

    public PresenterClientImpl(PresenterClient.View view) {
        this.view = view;
    }

    @Override
    public void implSelectAll() {
        new AsyncTask<Void, Void, List<Client>>() {
            @Override
            protected List<Client> doInBackground(Void... voids) {
                return ManageClient.getInstance().selectAll();
            }

            @Override
            protected void onPostExecute(List<Client> clientList) {
                super.onPostExecute(clientList);
                view.viewSelectAllResponse(clientList);
            }
        }.execute();
    }

    @Override
    public void implSelectOne(final int id) {
        new AsyncTask<Void, Void, Client>() {
            @Override
            protected Client doInBackground(Void... voids) {
                return ManageClient.getInstance().selectOne(id);
            }

            @Override
            protected void onPostExecute(Client client) {
                super.onPostExecute(client);
                view.viewSelectOneResponse(client);
            }
        }.execute();
    }

    @Override
    public void implInsert(final Client client) {
        new AsyncTask<Void, Void, Long>() {
            @Override
            protected Long doInBackground(Void... voids) {
                return ManageClient.getInstance().insert(client);
            }

            @Override
            protected void onPostExecute(Long aLong) {
                super.onPostExecute(aLong);
                view.viewInsertResponse(aLong);
            }
        }.execute();
    }

    @Override
    public void implUpdate(final Client client) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return ManageClient.getInstance().update(client);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.viewUpdateResponse(integer);
            }
        }.execute();
    }

    @Override
    public void implDelete(final Client client) {
        new AsyncTask<Void, Void, Integer>() {
            @Override
            protected Integer doInBackground(Void... voids) {
                return ManageClient.getInstance().delete(client);
            }

            @Override
            protected void onPostExecute(Integer integer) {
                super.onPostExecute(integer);
                view.viewDeleteResponse(integer);
            }
        }.execute();
    }
}

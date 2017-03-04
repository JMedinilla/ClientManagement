package deint.jvmed.clientmanagement.interfaces;

import java.util.List;

import deint.jvmed.clientmanagement.pojo.Client;

public interface PresenterClient {
    void implSelectAll();

    void implSelectOne(int id);

    void implInsert(Client client);

    void implUpdate(Client client);

    void implDelete(Client client);

    interface View {
        void viewSelectAllResponse(List<Client> clientList);

        void viewSelectOneResponse(Client client);

        void viewInsertResponse(long rsult);

        void viewUpdateResponse(int result);

        void viewDeleteResponse(int result);
    }
}

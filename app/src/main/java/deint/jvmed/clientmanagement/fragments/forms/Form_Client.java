package deint.jvmed.clientmanagement.fragments.forms;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.interfaces.ValidationPresenter;
import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.presenters.ValidationPresenterImpl;

public class Form_Client extends Fragment implements ValidationPresenter.ViewClient {
    private fragmentFormClientInterface callback;

    ValidationPresenterImpl validationPresenter;

    boolean update_mode;
    Client updatedPojo;

    EditText photo;
    EditText name;
    EditText surname;
    EditText location;
    EditText number;
    FloatingActionButton actionButton;

    public interface fragmentFormClientInterface {
        void fromFormToHome(Client client, boolean uppdate);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        update_mode = false;

        validationPresenter = new ValidationPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_client, container, false);
        photo = (EditText) view.findViewById(R.id.fromCliente_edtPhoto);
        name = (EditText) view.findViewById(R.id.fromCliente_edtName);
        surname = (EditText) view.findViewById(R.id.fromCliente_edtSurname);
        location = (EditText) view.findViewById(R.id.fromCliente_edtLocation);
        number = (EditText) view.findViewById(R.id.fromCliente_edtNumber);
        actionButton = (FloatingActionButton) view.findViewById(R.id.formCliente_btn);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update_mode) {
                    updatedPojo.setPhoto(photo.getText().toString());
                    updatedPojo.setName(name.getText().toString());
                    updatedPojo.setSurname(surname.getText().toString());
                    updatedPojo.setLocation(location.getText().toString());
                    updatedPojo.setNumber(number.getText().toString());

                    callback.fromFormToHome(updatedPojo, true);
                } else {
                    Client client = new Client(
                            photo.getText().toString(), name.getText().toString(),
                            surname.getText().toString(), location.getText().toString(),
                            number.getText().toString()
                    );

                    callback.fromFormToHome(client, false);
                }
            }
        });

        Client client = getArguments().getParcelable("openWithClient");
        if (client != null) {
            update_mode = true;
            updatedPojo = client;

            photo.setText(updatedPojo.getPhoto());
            name.setText(updatedPojo.getName());
            surname.setText(updatedPojo.getSurname());
            location.setText(updatedPojo.getLocation());
            number.setText(updatedPojo.getNumber());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (fragmentFormClientInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void validationResponse(int result) {
        String msg = "";
        switch (result) {
            case ValidationPresenter.ERROR_CLIENT_NAME_EMPTY:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_NAME_SHORT:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_NAME_LONG:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_SURNAME_EMPTY:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_SURNAME_SHORT:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_SURNAME_LONG:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_LOCATION_EMPTY:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_NUMBER_EMPTY:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_NUMBER_SHORT:
                msg = "";
                break;
            case ValidationPresenter.ERROR_CLIENT_NUMBER_LONG:
                msg = "";
                break;
            case ValidationPresenter.VALID_CLIENT:
                msg = "";
                break;
        }
        showResponseToast(msg);
    }

    public void showResponseToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

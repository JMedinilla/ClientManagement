package deint.jvmed.clientmanagement.fragments.forms;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.List;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.interfaces.ValidationPresenter;
import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.pojo.Meeting;
import deint.jvmed.clientmanagement.pojo.SpinnerClient;
import deint.jvmed.clientmanagement.presenters.ValidationPresenterImpl;

public class Form_Meeting extends Fragment implements ValidationPresenter.ViewMeeting {
    private fragmentFormMeetingInterface callback;

    ValidationPresenterImpl validationPresenter;

    boolean update_mode;
    boolean enoughClients;
    Meeting updatedPojo;

    Spinner clients;
    EditText edtDate;
    EditText edtStart;
    EditText edtEnd;
    EditText edtDescription;
    FloatingActionButton actionButton;

    String[] names;
    List<SpinnerClient> spinnerClients;

    public interface fragmentFormMeetingInterface {
        void fromFormToHome(Meeting meeting, boolean update);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        enoughClients = false;
        update_mode = false;

        validationPresenter = new ValidationPresenterImpl(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_form_meeting, container, false);
        clients = (Spinner) view.findViewById(R.id.formMeeting_spinner);
        edtDate = (EditText) view.findViewById(R.id.formMeeting_date);
        edtStart = (EditText) view.findViewById(R.id.formMeeting_start);
        edtEnd = (EditText) view.findViewById(R.id.formMeeting_end);
        edtDescription = (EditText) view.findViewById(R.id.formMeeting_description);
        actionButton = (FloatingActionButton) view.findViewById(R.id.formMeeting_btn);

        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (enoughClients) {
                    String selectedName = clients.getSelectedItem().toString();
                    int idClient = 0;
                    for (int i = 0; i < names.length; i++) {
                        if (selectedName.equals(spinnerClients.get(i).getName() + " " + spinnerClients.get(i).getSurname())) {
                            idClient = spinnerClients.get(i).getId();
                        }
                    }
                    String date = edtDate.getText().toString();
                    String startHour = edtStart.getText().toString();
                    String endHour = edtEnd.getText().toString();
                    String description = edtDescription.getText().toString();

                    Client client = new Client(
                            idClient, "default", "default", "default", "default", "default"
                    );
                    if (update_mode) {
                        updatedPojo.setClient(client);
                        updatedPojo.setDate(date);
                        updatedPojo.setStart(startHour);
                        updatedPojo.setEnd(endHour);
                        updatedPojo.setDescription(description);

                        validationPresenter.validateMeeting(updatedPojo);
                    } else {
                        Meeting meeting = new Meeting(client, date, startHour, endHour, description);

                        validationPresenter.validateMeeting(meeting);
                    }
                } else {
                    Toast.makeText(getContext(), R.string.no_clients_form, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Meeting meeting = getArguments().getParcelable("openWithMeeting");
        if (meeting != null) {
            update_mode = true;
            updatedPojo = meeting;

            edtDate.setText(meeting.getDate());
            edtStart.setText(meeting.getStart());
            edtEnd.setText(meeting.getEnd());
            edtDescription.setText(meeting.getDescription());
        }

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        validationPresenter.implSelectSpinnerClients();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (fragmentFormMeetingInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void validationResponse(int result, Meeting pMe) {
        boolean valid = false;
        String msg = "";
        switch (result) {
            case ValidationPresenter.ERROR_MEETING_DATE_FORMAT:
                msg = getString(R.string.ERROR_MEETING_DATE_FORMAT);
                break;
            case ValidationPresenter.ERROR_MEETING_HOUR_START_FORMAT:
                msg = getString(R.string.ERROR_MEETING_HOUR_START_FORMAT);
                break;
            case ValidationPresenter.ERROR_MEETING_HOUR_END_FORMAT:
                msg = getString(R.string.ERROR_MEETING_HOUR_END_FORMAT);
                break;
            case ValidationPresenter.ERROR_MEETING_HOUR_END_BEFORE:
                msg = getString(R.string.ERROR_MEETING_HOUR_END_BEFORE);
                break;
            case ValidationPresenter.ERROR_MEETING_DESCRIPTION_EMPTY:
                msg = getString(R.string.ERROR_MEETING_DESCRIPTION_EMPTY);
                break;
            case ValidationPresenter.ERROR_MEETING_DESCRIPTION_SHORT:
                msg = getString(R.string.ERROR_MEETING_DESCRIPTION_SHORT);
                break;
            case ValidationPresenter.ERROR_MEETING_DESCRIPTION_LONG:
                msg = getString(R.string.ERROR_MEETING_DESCRIPTION_LONG);
                break;
            case ValidationPresenter.VALID_MEETING:
                valid = true;
                if (update_mode) {
                    callback.fromFormToHome(pMe, true);
                } else {
                    callback.fromFormToHome(pMe, false);
                }
                break;
        }
        if (!valid) {
            showResponseToast(msg);
        }
    }

    @Override
    public void viewSelectSpinnerClientsResponse(List<SpinnerClient> spinnerClients) {
        if (spinnerClients.size() > 0) {
            enoughClients = true;
        }
        this.spinnerClients = spinnerClients;
        names = new String[spinnerClients.size()];

        for (int i = 0; i < spinnerClients.size(); i++) {
            names[i] = spinnerClients.get(i).getName() + " " + spinnerClients.get(i).getSurname();
        }

        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, names);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        clients.setAdapter(adapter);
    }

    public void showResponseToast(String msg) {
        Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}

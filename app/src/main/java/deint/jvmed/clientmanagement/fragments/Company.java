package deint.jvmed.clientmanagement.fragments;

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

import java.util.Objects;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.preferences.Profile;

public class Company extends Fragment {
    private fragmentCompanyInterface callback;

    EditText edtName;
    EditText edtCif;
    EditText edtLocation;
    EditText edtPhone;
    EditText edtMail;
    FloatingActionButton btnSave;

    Profile profile;

    public interface fragmentCompanyInterface {
        void companyCreated();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        profile = new Profile(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        edtName = (EditText) view.findViewById(R.id.profile_name);
        edtCif = (EditText) view.findViewById(R.id.profile_cif);
        edtLocation = (EditText) view.findViewById(R.id.profile_location);
        edtPhone = (EditText) view.findViewById(R.id.profile_phone);
        edtMail = (EditText) view.findViewById(R.id.profile_mail);
        btnSave = (FloatingActionButton) view.findViewById(R.id.profile_button);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString();
                String cif = edtCif.getText().toString();
                String location = edtLocation.getText().toString();
                String phone = edtPhone.getText().toString();
                String mail = edtMail.getText().toString();

                if (!Objects.equals(name, "") && !(name.length() < 6)) {
                    if (!Objects.equals(cif, "") && !(cif.length() < 6)) {
                        if (!Objects.equals(location, "") && !(location.length() < 6)) {
                            if (!Objects.equals(phone, "") && phone.length() == 9) {
                                if (!Objects.equals(mail, "")) {
                                    profile.setName(name);
                                    profile.setCif(cif);
                                    profile.setLocation(location);
                                    profile.setPhone(phone);
                                    profile.setEmail(mail);
                                    callback.companyCreated();
                                } else {
                                    Toast.makeText(getContext(), "Mail is empty", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Phone has to be 9 numbers long", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(getContext(), "Location need 6 or more characters", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getContext(), "CIF need 6 or more characters", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Name need 6 or more characters", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (!profile.getName().isEmpty()) {
            edtName.setText(profile.getName());
            edtCif.setText(profile.getCif());
            edtLocation.setText(profile.getLocation());
            edtPhone.setText(profile.getPhone());
            edtMail.setText(profile.getEmail());
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (fragmentCompanyInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}

package deint.jvmed.clientmanagement.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import deint.jvmed.clientmanagement.R;

public class Home extends Fragment {
    private fragmentHomeInterface callback;

    Button btnCompany;
    Button btnClient;
    Button btnMeeting;

    public interface fragmentHomeInterface {
        void openCompanyFragment();

        void openClientFragment();

        void openMeetingFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        btnCompany = (Button) view.findViewById(R.id.home_btnProfile);
        btnClient = (Button) view.findViewById(R.id.home_btnClient);
        btnMeeting = (Button) view.findViewById(R.id.home_btnMeeting);
        btnCompany.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openCompanyFragment();
            }
        });
        btnClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openClientFragment();
            }
        });
        btnMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openMeetingFragment();
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (fragmentHomeInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }
}

package deint.jvmed.clientmanagement;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import deint.jvmed.clientmanagement.fragments.Company;
import deint.jvmed.clientmanagement.fragments.Diary;
import deint.jvmed.clientmanagement.fragments.Home;
import deint.jvmed.clientmanagement.fragments.forms.Form_Client;
import deint.jvmed.clientmanagement.fragments.forms.Form_Meeting;
import deint.jvmed.clientmanagement.fragments.lists.List_Client;
import deint.jvmed.clientmanagement.fragments.lists.List_Meeting;
import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.pojo.Meeting;
import deint.jvmed.clientmanagement.preferences.Profile;
import deint.jvmed.clientmanagement.receivers.InsertReceiver;

public class Activity_Home extends AppCompatActivity implements
        Home.fragmentHomeInterface, List_Client.fragmentListClientInterface, List_Meeting.fragmentListMeetingInterface,
        Form_Client.fragmentFormClientInterface, Form_Meeting.fragmentFormMeetingInterface, Company.fragmentCompanyInterface {

    Home fragmentHome;
    List_Client fragmentListClient;
    List_Meeting fragmentListMeeting;
    Company fragmentCompany;
    Form_Client fragmentFormClient;
    Form_Meeting fragmentFormMeeting;
    Diary fragmentDiary;

    Profile profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        profile = new Profile(Activity_Home.this);

        fragmentHome = new Home();
        fragmentListClient = new List_Client();
        fragmentListMeeting = new List_Meeting();

        int num = 0;
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                num = bundle.getInt("openFromNotification");
            }
        }

        if (num == InsertReceiver.OPEN_FRAGMENT_FROM_NOTIFICATION) {
            openClientFragment();
        } else {
            if (profile.getName().isEmpty()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Home.this);
                builder.setCancelable(false);
                builder.setTitle(R.string.TitleAlert);
                builder.setMessage(R.string.MessageAlert);
                builder.setPositiveButton(R.string.AcceptAlert, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openCompanyFragment();
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            } else {
                showHome();
            }
        }
    }

    public void showHome() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentHome, "fragmentHome");
        fragmentTransaction.addToBackStack("fragmentHome");
        fragmentTransaction.commit();
    }

    @Override
    public void openCompanyFragment() {
        fragmentCompany = new Company();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentCompany, "fragmentCompany");
        fragmentTransaction.addToBackStack("fragmentCompany");
        fragmentTransaction.commit();
    }

    @Override
    public void openClientFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentListClient, "fragmentListClient");
        fragmentTransaction.addToBackStack("fragmentListClient");
        fragmentTransaction.commit();
    }

    @Override
    public void openMeetingFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentListMeeting, "fragmentListMeeting");
        fragmentTransaction.addToBackStack("fragmentListMeeting");
        fragmentTransaction.commit();
    }

    @Override
    public void openFormClientFragment(Client client) {
        fragmentFormClient = new Form_Client();
        Bundle bundle = new Bundle();
        bundle.putParcelable("openWithClient", client);
        fragmentFormClient.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentFormClient, "fragmentFormClient");
        fragmentTransaction.addToBackStack("fragmentFormClient");
        fragmentTransaction.commit();
    }

    @Override
    public void openFormMeetingFragment(Meeting meeting) {
        fragmentFormMeeting = new Form_Meeting();
        Bundle bundle = new Bundle();
        bundle.putParcelable("openWithMeeting", meeting);
        fragmentFormMeeting.setArguments(bundle);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.activity_home, fragmentFormMeeting, "fragmentFormMeeting");
        fragmentTransaction.addToBackStack("fragmentFormMeeting");
        fragmentTransaction.commit();
    }

    @Override
    public void fromFormToHome(Client client, boolean uppdate) {
        fragmentListClient.fromHomeToList(client, uppdate);
    }

    @Override
    public void fromFormToHome(Meeting meeting, boolean update) {
        fragmentListMeeting.fromHomeToList(meeting, update);
    }

    @Override
    public void companyCreated() {
        showHome();
    }
}

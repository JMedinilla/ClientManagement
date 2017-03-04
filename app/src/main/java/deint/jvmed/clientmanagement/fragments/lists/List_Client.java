package deint.jvmed.clientmanagement.fragments.lists;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.adapters.Adapter_Client;
import deint.jvmed.clientmanagement.interfaces.PresenterClient;
import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.presenters.PresenterClientImpl;
import deint.jvmed.clientmanagement.receivers.InsertReceiver;

public class List_Client extends Fragment implements PresenterClient.View {
    private fragmentListClientInterface callback;
    boolean update;

    ListView listView;
    FloatingActionButton actionButton;

    PresenterClientImpl presenterClient;
    Adapter_Client adapterClient;

    @Override
    public void viewSelectAllResponse(List<Client> clientList) {
        adapterClient.updateList(clientList);
    }

    @Override
    public void viewSelectOneResponse(final Client client) {
        if (update) {
            callback.openFormClientFragment(client);
            update = false;
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_client, null);
            ImageView photo = (ImageView) view.findViewById(R.id.dialogClient_photo);
            photo.setImageResource(R.mipmap.ic_launcher);
            TextView name = (TextView) view.findViewById(R.id.dialogClient_name);
            name.setText(client.getName());
            TextView surname = (TextView) view.findViewById(R.id.dialogClient_surname);
            surname.setText(client.getSurname());
            TextView location = (TextView) view.findViewById(R.id.dialogClient_location);
            location.setText(client.getLocation());
            TextView number = (TextView) view.findViewById(R.id.dialogClient_number);
            number.setText(client.getNumber());

            ImageButton map = (ImageButton) view.findViewById(R.id.dialogClient_btnMap);
            ImageButton call = (ImageButton) view.findViewById(R.id.dialogClient_btnCall);
            map.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String uri = String.format(Locale.getDefault(), "geo:0,0?q=%s", client.getLocation());
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setPackage("com.google.android.apps.maps");
                    if (intent.resolveActivity(getContext().getPackageManager()) != null) {
                        getContext().startActivity(intent);
                    } else {
                        Toast.makeText(getContext(), R.string.no_maps, Toast.LENGTH_SHORT).show();
                    }
                }
            });
            call.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_CALL);
                    intent.setData(Uri.parse("tel:" + client.getNumber()));
                    if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    getContext().startActivity(intent);
                }
            });

            builder.setView(view);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Override
    public void viewInsertResponse(long rsult) {
        if (rsult != -1) {
            Toast.makeText(getContext(), R.string.insertsuccess, Toast.LENGTH_SHORT).show();
            getActivity().sendBroadcast(new Intent(InsertReceiver.ACTION_INSERT));
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), R.string.inserterror, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void viewUpdateResponse(int result) {
        if (result != 0) {
            Toast.makeText(getContext(), R.string.updatesuccess, Toast.LENGTH_SHORT).show();
            getActivity().onBackPressed();
        } else {
            Toast.makeText(getContext(), R.string.updateerror, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void viewDeleteResponse(int result) {
        if (result != 0) {
            Toast.makeText(getContext(), R.string.deletesuccess, Toast.LENGTH_SHORT).show();

            presenterClient.implSelectAll();
        } else {
            Toast.makeText(getContext(), R.string.deleteerror, Toast.LENGTH_SHORT).show();
        }
    }

    public void fromHomeToList(Client client, boolean uppdate) {
        if (uppdate) {
            presenterClient.implUpdate(client);
        } else {
            presenterClient.implInsert(client);
        }
    }

    public interface fragmentListClientInterface {
        void openFormClientFragment(Client client);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        update = false;

        presenterClient = new PresenterClientImpl(this);
        adapterClient = new Adapter_Client(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_client, container, false);
        listView = (ListView) view.findViewById(R.id.client_list);
        actionButton = (FloatingActionButton) view.findViewById(R.id.client_btnForm);
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callback.openFormClientFragment(null);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Client client = adapterClient.getItem(i);
                if (client != null) {
                    presenterClient.implSelectOne(client.getId());
                }
            }
        });
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView.setAdapter(adapterClient);
        registerForContextMenu(listView);

        presenterClient.implSelectAll();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callback = (fragmentListClientInterface) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callback = null;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Client client = adapterClient.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.contextUpdate:
                callback.openFormClientFragment(client);
                break;
            case R.id.contextDelete:
                presenterClient.implDelete(client);
                break;
        }
        return super.onContextItemSelected(item);
    }
}

package deint.jvmed.clientmanagement.adapters;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Locale;

import deint.jvmed.clientmanagement.R;
import deint.jvmed.clientmanagement.pojo.Client;

public class Adapter_Client extends ArrayAdapter<Client> {
    private Context context;

    public Adapter_Client(Context context) {
        super(context, R.layout.adapter_client);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ClientHolder clientHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.adapter_client, parent, false);
            clientHolder = new ClientHolder();
            clientHolder.name = (TextView) view.findViewById(R.id.adapterClient_name);
            view.setTag(clientHolder);
        } else {
            clientHolder = (ClientHolder) view.getTag();
        }

        final Client client = getItem(position);
        if (client != null) {
            clientHolder.name.setText(client.getName());
        }

        return view;
    }

    @Nullable
    @Override
    public Client getItem(int position) {
        return super.getItem(position);
    }

    public void updateList(List<Client> clientList) {
        clear();
        if (clientList != null) {
            if (clientList.size() > 0) {
                addAll(clientList);
            }
        }
        notifyDataSetChanged();
    }

    private class ClientHolder {
        TextView name;
    }
}

package deint.jvmed.clientmanagement.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.pojo.SpinnerClient;

public class ManageClient {
    private static ManageClient instance;

    public static ManageClient getInstance() {
        if (instance == null) {
            instance = new ManageClient();
        }
        return instance;
    }

    public List<Client> selectAll() {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.ClientTable.TABLE_NAME,
                DatabaseContract.ClientTable.ALL_COLUMNS, null, null, null, null, null);

        ArrayList<Client> arrayList = new ArrayList<>();
        Client client;
        if (cursor.moveToFirst()) {
            do {
                client = new Client(
                        cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5)
                );
                arrayList.add(client);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return arrayList;
    }

    public Client selectOne(int id) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.ClientTable.TABLE_NAME,
                DatabaseContract.ClientTable.ALL_COLUMNS, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);
        Client client = null;
        if (cursor.moveToFirst()) {
            client = new Client(
                    cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5)
            );
        }

        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return client;
    }

    public long insert(Client client) {
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        values.put(DatabaseContract.ClientTable.COLUMN_PHOTO, client.getPhoto());
        values.put(DatabaseContract.ClientTable.COLUMN_NAME, client.getName());
        values.put(DatabaseContract.ClientTable.COLUMN_SURNAME, client.getSurname());
        values.put(DatabaseContract.ClientTable.COLUMN_LOCATION, client.getLocation());
        values.put(DatabaseContract.ClientTable.COLUMN_NUMBER, client.getNumber());
        long result = sqLiteDatabase.insert(DatabaseContract.ClientTable.TABLE_NAME, null, values);
        DatabaseHelper.getInstance().closeDatabase();
        return result;
    }

    public int update(Client client) {
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        values.put(DatabaseContract.ClientTable.COLUMN_PHOTO, client.getPhoto());
        values.put(DatabaseContract.ClientTable.COLUMN_NAME, client.getName());
        values.put(DatabaseContract.ClientTable.COLUMN_SURNAME, client.getSurname());
        values.put(DatabaseContract.ClientTable.COLUMN_LOCATION, client.getLocation());
        values.put(DatabaseContract.ClientTable.COLUMN_NUMBER, client.getNumber());
        int result = sqLiteDatabase.update(DatabaseContract.ClientTable.TABLE_NAME,
                values, "_id = ?", new String[]{String.valueOf(client.getId())});
        DatabaseHelper.getInstance().closeDatabase();
        return result;
    }

    public int delete(Client client) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        int result = sqLiteDatabase.delete(DatabaseContract.ClientTable.TABLE_NAME,
                "_id = ?", new String[]{String.valueOf(client.getId())});
        DatabaseHelper.getInstance().closeDatabase();
        return result;
    }

    public List<SpinnerClient> selectAllSpinner() {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.ClientTable.TABLE_NAME,
                new String[]{DatabaseContract.ClientTable.COLUMN_ID, DatabaseContract.ClientTable.COLUMN_NAME,
                        DatabaseContract.ClientTable.COLUMN_SURNAME}, null, null, null, null, null);

        ArrayList<SpinnerClient> arrayList = new ArrayList<>();
        SpinnerClient client;
        if (cursor.moveToFirst()) {
            do {
                client = new SpinnerClient(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                arrayList.add(client);
            }
            while (cursor.moveToNext());
        }

        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return arrayList;
    }
}

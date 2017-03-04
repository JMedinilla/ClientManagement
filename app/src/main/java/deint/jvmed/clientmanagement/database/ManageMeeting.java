package deint.jvmed.clientmanagement.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import deint.jvmed.clientmanagement.pojo.Client;
import deint.jvmed.clientmanagement.pojo.Meeting;

public class ManageMeeting {
    private static ManageMeeting instance;

    public static ManageMeeting getInstance() {
        if (instance == null) {
            instance = new ManageMeeting();
        }
        return instance;
    }

    public List<Meeting> selectAll() {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.MeetingTable.TABLE_NAME,
                DatabaseContract.MeetingTable.ALL_COLUMNS, null, null, null, null, null);

        ArrayList<Meeting> arrayList = new ArrayList<>();
        Meeting meeting;
        if (cursor.moveToFirst()) {
            do {
                Client pojo = null;
                int id = cursor.getInt(0);
                int client = cursor.getInt(1);
                String date = cursor.getString(2);
                String start = cursor.getString(3);
                String end = cursor.getString(4);
                String description = cursor.getString(5);

                Cursor cursorClient = sqLiteDatabase.query(DatabaseContract.ClientTable.TABLE_NAME,
                        DatabaseContract.ClientTable.ALL_COLUMNS, "_id = ?", new String[]{String.valueOf(client)}, null, null, null);

                if (cursorClient.moveToFirst()) {
                    pojo = new Client(cursorClient.getInt(0), cursorClient.getString(1), cursorClient.getString(2),
                            cursorClient.getString(3), cursorClient.getString(4), cursorClient.getString(5));
                }
                cursorClient.close();

                meeting = new Meeting(id, pojo, date, start, end, description);
                arrayList.add(meeting);
            } while (cursor.moveToNext());
        }
        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return arrayList;
    }

    public Meeting selectOne(int id) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        Cursor cursor = sqLiteDatabase.query(DatabaseContract.MeetingTable.TABLE_NAME,
                DatabaseContract.MeetingTable.ALL_COLUMNS, "_id = ?", new String[]{String.valueOf(id)}, null, null, null);

        Meeting meeting = null;
        if (cursor.moveToFirst()) {
            Client pojo = null;
            int idMeeting = cursor.getInt(0);
            int client = cursor.getInt(1);
            String date = cursor.getString(2);
            String start = cursor.getString(3);
            String end = cursor.getString(4);
            String description = cursor.getString(5);

            Cursor cursorClient = sqLiteDatabase.query(DatabaseContract.ClientTable.TABLE_NAME,
                    DatabaseContract.ClientTable.ALL_COLUMNS, "_id = ?", new String[]{String.valueOf(client)}, null, null, null);

            if (cursorClient.moveToFirst()) {
                pojo = new Client(cursorClient.getInt(0), cursorClient.getString(1), cursorClient.getString(2),
                        cursorClient.getString(3), cursorClient.getString(4), cursorClient.getString(5));
            }
            cursorClient.close();

            meeting = new Meeting(idMeeting, pojo, date, start, end, description);
        }
        cursor.close();
        DatabaseHelper.getInstance().closeDatabase();
        return meeting;
    }

    public long insert(Meeting meeting) {
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        values.put(DatabaseContract.MeetingTable.COLUMN_CLIENT, meeting.getClient().getId());
        values.put(DatabaseContract.MeetingTable.COLUMN_DATE, meeting.getDate());
        values.put(DatabaseContract.MeetingTable.COLUMN_START, meeting.getStart());
        values.put(DatabaseContract.MeetingTable.COLUMN_END, meeting.getEnd());
        values.put(DatabaseContract.MeetingTable.COLUMN_DESCRIPTION, meeting.getDescription());
        long result = sqLiteDatabase.insert(DatabaseContract.MeetingTable.TABLE_NAME, null, values);
        DatabaseHelper.getInstance().closeDatabase();
        return result;
    }

    public int update(Meeting meeting) {
        ContentValues values = new ContentValues();
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        values.put(DatabaseContract.MeetingTable.COLUMN_CLIENT, meeting.getClient().getId());
        values.put(DatabaseContract.MeetingTable.COLUMN_DATE, meeting.getDate());
        values.put(DatabaseContract.MeetingTable.COLUMN_START, meeting.getStart());
        values.put(DatabaseContract.MeetingTable.COLUMN_END, meeting.getEnd());
        values.put(DatabaseContract.MeetingTable.COLUMN_DESCRIPTION, meeting.getDescription());
        int result = sqLiteDatabase.update(DatabaseContract.MeetingTable.TABLE_NAME,
                values, "_id = ?", new String[]{String.valueOf(meeting.getId())});
        DatabaseHelper.getInstance().closeDatabase();
        return result;
    }

    public int delete(Meeting meeting) {
        SQLiteDatabase sqLiteDatabase = DatabaseHelper.getInstance().openDatabase();
        int result = sqLiteDatabase.delete(DatabaseContract.MeetingTable.TABLE_NAME,
                "_id = ?", new String[]{String.valueOf(meeting.getId())});
        DatabaseHelper.getInstance().closeDatabase();
        return result;
    }
}

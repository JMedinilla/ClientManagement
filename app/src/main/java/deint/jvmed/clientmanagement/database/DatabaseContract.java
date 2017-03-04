package deint.jvmed.clientmanagement.database;

import android.provider.BaseColumns;

class DatabaseContract {
    static class ClientTable implements BaseColumns {
        static final String TABLE_NAME = "clients";
        static final String COLUMN_ID = _ID;
        static final String COLUMN_PHOTO = "cl_photo";
        static final String COLUMN_NAME = "cl_name";
        static final String COLUMN_SURNAME = "cl_surname";
        static final String COLUMN_LOCATION = "cl_location";
        static final String COLUMN_NUMBER = "cl_number";

        static final String[] ALL_COLUMNS = new String[]{
                COLUMN_ID, COLUMN_PHOTO, COLUMN_NAME,
                COLUMN_SURNAME, COLUMN_LOCATION, COLUMN_NUMBER
        };

        static final String SQL_CREATE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT NOT NULL,"
                        + " %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
                TABLE_NAME, COLUMN_ID, COLUMN_PHOTO, COLUMN_NAME,
                COLUMN_SURNAME, COLUMN_LOCATION, COLUMN_NUMBER
        );

        static final String SQL_DELETE = String.format(
                "DROP TABLE IF EXISTS %s", TABLE_NAME
        );
    }

    static class MeetingTable implements BaseColumns {
        static final String TABLE_NAME = "meetings";
        static final String COLUMN_ID = _ID;
        static final String COLUMN_CLIENT = "me_client";
        static final String COLUMN_DATE = "me_date";
        static final String COLUMN_START = "me_start";
        static final String COLUMN_END = "me_end";
        static final String COLUMN_DESCRIPTION = "me_description";

        static final String[] ALL_COLUMNS = new String[]{
                COLUMN_ID, COLUMN_CLIENT, COLUMN_DATE,
                COLUMN_START, COLUMN_END, COLUMN_DESCRIPTION
        };

        static final String REFERENCE_TO_CLIENT = String.format(
                "REFERENCES %s (%s) ON UPDATE CASCADE ON DELETE RESTRICT",
                ClientTable.TABLE_NAME, ClientTable.COLUMN_ID
        );

        static final String SQL_CREATE = String.format(
                "CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s INTEGER NOT NULL %s,"
                        + " %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL)",
                TABLE_NAME, COLUMN_ID, COLUMN_CLIENT, REFERENCE_TO_CLIENT, COLUMN_DATE,
                COLUMN_START, COLUMN_END, COLUMN_DESCRIPTION
        );

        static final String SQL_DELETE = String.format(
                "DROP TABLE IF EXISTS %s", TABLE_NAME
        );
    }
}

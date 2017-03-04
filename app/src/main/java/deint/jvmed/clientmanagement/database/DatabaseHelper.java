package deint.jvmed.clientmanagement.database;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import deint.jvmed.clientmanagement.others.MyApplication;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static volatile DatabaseHelper instance;
    private static SQLiteDatabase database;

    private static final String DATABASE_NAME = "clientmanagement.db";
    private static final int DATABASE_VERSION = 1;

    private DatabaseHelper() {
        super(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
    }

    public synchronized static DatabaseHelper getInstance() {
        if (instance == null) {
            instance = new DatabaseHelper();
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(DatabaseContract.ClientTable.SQL_CREATE);
            sqLiteDatabase.execSQL(DatabaseContract.MeetingTable.SQL_CREATE);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.beginTransaction();
        try {
            sqLiteDatabase.execSQL(DatabaseContract.MeetingTable.SQL_DELETE);
            sqLiteDatabase.execSQL(DatabaseContract.ClientTable.SQL_DELETE);
            onCreate(sqLiteDatabase);
            sqLiteDatabase.setTransactionSuccessful();
        } finally {
            sqLiteDatabase.endTransaction();
        }
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                db.setForeignKeyConstraintsEnabled(true);
            } else {
                db.execSQL("PRAGMA foreign_keys = ON");
            }
        }
    }

    public SQLiteDatabase open() {
        return getWritableDatabase();
    }

    SQLiteDatabase openDatabase() {
        database = getWritableDatabase();
        return database;
    }

    void closeDatabase() {
        database.close();
    }
}

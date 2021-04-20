package ca.mohawkcollege.fayad;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String TAG = "==MyDbHelper==";
    /**
     * collection of DataBase field names
     **/
    public static final String DATABASE_FILE_NAME = "MyDatabase.db1";
    public static final int DATABASE_VERSION = 1;
    public static final String BOOKTABLE = "books";
    public static final String ID = "_id";
    public static final String NAME = "name";
    public static final String IMGURL = "imgUrl";
    public static final String AUTHOR = "grade";

    private static final String SQL_CREATE =
            "CREATE TABLE " + BOOKTABLE + " ( " + ID + " INTEGER PRIMARY KEY, " +
                    NAME + " TEXT, " + IMGURL + " TEXT, "+ AUTHOR + " DOUBLE) ";

    public MyDBHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate " + SQL_CREATE);
        db.execSQL(SQL_CREATE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

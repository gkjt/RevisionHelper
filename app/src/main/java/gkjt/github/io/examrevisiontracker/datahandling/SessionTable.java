package gkjt.github.io.examrevisiontracker.datahandling;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by GTucker on 30/08/2015.
 */
public class SessionTable {
    public static final String TABLE_SESSIONS = "sessions";
    public static final String COL_ID = "session_id";
    public static final String COL_DURATION = "duration";
    public static final String COL_TIME = "time";
    public static final String COL_EXAM = "exam_id";
    public static final String COL_SUBJECT = "subject_id";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_SESSIONS
            + " ("
            + COL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_DURATION
            + " INTEGER NOT NULL, "
            + COL_TIME
            + " INTEGER NOT NULL"
            + COL_EXAM
            + " INTEGER NOT NULL"
            + COL_SUBJECT
            + " INTEGER NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        Log.w(SessionTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_SESSIONS);
        onCreate(database);
    }


}

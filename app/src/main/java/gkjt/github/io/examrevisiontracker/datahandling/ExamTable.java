package gkjt.github.io.examrevisiontracker.datahandling;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by GTucker on 30/08/2015.
 */
public class ExamTable {
    public static final String TABLE_EXAMS = "exams";
    public static final String COL_ID = "_id";
    public static final String COL_TITLE = "title";
    public static final String COL_TIME = "time";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_EXAMS
            + " ("
            + COL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE
            + " TEXT NOT NULL, "
            + COL_TIME
            + " INTEGER NOT NULL"
            + ");";

    public static void onCreate(SQLiteDatabase database){
        database.execSQL(TABLE_CREATE);
    }

    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.w(ExamTable.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_EXAMS);
        onCreate(database);
    }
}

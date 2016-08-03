package gkjt.github.io.examrevisiontracker.datahandling;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by User on 01/08/2016.
 */
public class SubjectTable {
    public static final String TABLE_EXAMS = "subject";
    public static final String COL_ID = "subject_id";
    public static final String COL_TITLE = "title";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_EXAMS
            + " ("
            + COL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE
            + " TEXT NOT NULL, "
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

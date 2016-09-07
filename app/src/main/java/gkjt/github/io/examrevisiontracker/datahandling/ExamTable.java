package gkjt.github.io.examrevisiontracker.datahandling;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by GTucker on 30/08/2015.
 */
public class ExamTable {
    public static final String TABLE_EXAMS = "exams";
    public static final String COL_ID = "exam_id";
    public static final String COL_TITLE = "title";
    public static final String COL_TIME_REVISED = "time_revised";
    public static final String COL_SUBJECT = "subject";
    public static final String COL_DATE = "date";
    public static final String COL_GRADE_PERCENTAGE = "percentage_grade";

    private static final String TABLE_CREATE = "CREATE TABLE " + TABLE_EXAMS
            + " ("
            + COL_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_TITLE
            + " TEXT NOT NULL, "
            + COL_TIME_REVISED
            + " INTEGER NOT NULL, "
            + COL_SUBJECT
            + " INTEGER NOT NULL, "
            + COL_DATE
            + " INTEGER"
            + COL_GRADE_PERCENTAGE
            + " INTEGER"
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

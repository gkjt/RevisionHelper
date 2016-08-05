package gkjt.github.io.examrevisiontracker.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gkjt.github.io.examrevisiontracker.Exam;
import gkjt.github.io.examrevisiontracker.Session;

/**
 * Created by GTucker on 30/08/2015.
 */
public class RevisionDataHelper extends SQLiteOpenHelper {
	//TODO:Rename columns like time revised to be more explicit
    private static final String DATABASE_NAME = "revision_database";
    private static final int DATABASE_VERSION = 1;

    public RevisionDataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        ExamTable.onCreate(database);
        SessionTable.onCreate(database);
		SubjectTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer){
        ExamTable.onUpgrade(database, oldVer, newVer);
        SessionTable.onUpgrade(database, oldVer, newVer);
		SubjectTable.onUpgrade(database, oldVer, newVer);
    }

	public void closeDB() {
		SQLiteDatabase db = getReadableDatabase();
		if (db != null && db.isOpen()) {
			db.close();
		}
	}










}

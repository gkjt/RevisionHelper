package gkjt.github.io.examrevisiontracker.datahandling;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

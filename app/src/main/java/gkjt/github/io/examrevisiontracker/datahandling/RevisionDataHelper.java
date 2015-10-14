package gkjt.github.io.examrevisiontracker.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.Exam;
import gkjt.github.io.examrevisiontracker.Session;

/**
 * Created by GTucker on 30/08/2015.
 */
public class RevisionDataHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "revision_database";
    private static final int DATABASE_VERSION = 1;

    public RevisionDataHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database){
        ExamTable.onCreate(database);
        SessionTable.onCreate(database);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer){
        ExamTable.onUpgrade(database, oldVer, newVer);
        SessionTable.onUpgrade(database, oldVer, newVer);
    }

    public long createExam(Exam exam){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ExamTable.COL_TIME, exam.getTime());
        values.put(ExamTable.COL_TITLE, exam.getTitle());

        return db.insert(ExamTable.TABLE_EXAMS, null, values);
    }

    public long createSession(Session session){
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(SessionTable.COL_TIME, session.getTime());
        values.put(SessionTable.COL_DURATION, session.getTime());

        return db.insert(SessionTable.TABLE_SESSIONS, null, values);
    }

    public Exam getExam(long id){
        SQLiteDatabase db = getReadableDatabase();

        String select = "SELECT * FROM " + ExamTable.TABLE_EXAMS + " WHERE "
                + ExamTable.COL_ID + " = " + id;
        Cursor curs = db.rawQuery(select, null);

        if (curs != null) {
            curs.moveToFirst();

            Exam exam = new Exam();

            exam.setId(curs.getLong(curs.getColumnIndex(ExamTable.COL_ID)));
            exam.setTime(curs.getLong(curs.getColumnIndex(ExamTable.COL_TIME)));
            exam.setTitle(curs.getString(curs.getColumnIndex(ExamTable.COL_TITLE)));

            return exam;
        }

        return null;
    }

    public Session getSession(long id){
        SQLiteDatabase db = getReadableDatabase();

        String select = "SELECT * FROM " + ExamTable.TABLE_EXAMS + " WHERE "
                + ExamTable.COL_ID + " = " + id;
        Cursor curs = db.rawQuery(select, null);

        if (curs != null) {
            curs.moveToFirst();

            Session session = new Session();

            session.setId(curs.getLong(curs.getColumnIndex(SessionTable.COL_ID)));
            session.setTime(curs.getLong(curs.getColumnIndex(SessionTable.COL_TIME)));
            session.setDuration(curs.getLong(curs.getColumnIndex(SessionTable.COL_DURATION)));

            return session;
        }

        return null;
    }

    public List<Exam> getExams(){
		List<Exam> exams = new ArrayList<Exam>();

		SQLiteDatabase db = getReadableDatabase();

		String select = "SELECT * FROM " + ExamTable.TABLE_EXAMS;
		Cursor curs = db.rawQuery(select, null);

		if(curs.moveToFirst()){
			do{
				Exam exam = new Exam();

				exam.setId(curs.getLong(curs.getColumnIndex(ExamTable.COL_ID)));
				exam.setTime(curs.getLong(curs.getColumnIndex(ExamTable.COL_TIME)));
				exam.setTitle(curs.getString(curs.getColumnIndex(ExamTable.COL_TITLE)));

				exams.add(exam);
			}while(curs.moveToNext());

			return exams;
		}

        return null;
    }

    public List<Session> getSessions(){
        SQLiteDatabase db = getReadableDatabase();
		List<Session> sessions = new ArrayList<Session>();

		String select = "SELECT * FROM " + SessionTable.TABLE_SESSIONS;
		Cursor curs = db.rawQuery(select, null);

		if(curs.moveToFirst()){
			do{
				Session session = new Session();

				session.setId(curs.getLong(curs.getColumnIndex(SessionTable.COL_ID)));
				session.setDuration(curs.getLong(curs.getColumnIndex(SessionTable.COL_DURATION)));
				session.setTime(curs.getLong(curs.getColumnIndex(SessionTable.COL_TIME)));

				sessions.add(session);
			}while(curs.moveToNext());

			return sessions;
		}

		return null;
    }

	public int updateExam(Exam exam){
		SQLiteDatabase db = getWritableDatabase();

		ContentValues vals = new ContentValues();

		vals.put(ExamTable.COL_TITLE, exam.getTitle());
		vals.put(ExamTable.COL_TIME, exam.getTime());

		return db.update(
				ExamTable.TABLE_EXAMS,
				vals,
				ExamTable.COL_ID + " = ?",
				new String[]{String.valueOf(exam.getId())}
		);
	}

	public int updateSession(Session session){
		SQLiteDatabase db = getWritableDatabase();

		ContentValues vals = new ContentValues();

		vals.put(SessionTable.COL_DURATION, session.getDuration());
		vals.put(SessionTable.COL_TIME, session.getTime());

		return db.update(
				SessionTable.TABLE_SESSIONS,
				vals,
				SessionTable.COL_ID + " = ?",
				new String[]{String.valueOf(session.getId())}
		);
	}

	public void deleteExam(long examId){
		SQLiteDatabase db = getWritableDatabase();

		db.delete(
				ExamTable.TABLE_EXAMS,
				ExamTable.COL_ID + " = ?",
				new String[]{String.valueOf(examId)}
		);
	}

	public void deleteSession(long sessionId){
		SQLiteDatabase db = getWritableDatabase();

		db.delete(
				SessionTable.TABLE_SESSIONS,
				SessionTable.COL_ID  + " = ?",
				new String[]{String.valueOf(sessionId)}
		);
	}

	public void closeDB(){
		SQLiteDatabase db = getReadableDatabase();
		if(db!=null && db.isOpen()){
			db.close();
		}
	}

}

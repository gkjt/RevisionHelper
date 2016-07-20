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
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVer, int newVer){
        ExamTable.onUpgrade(database, oldVer, newVer);
        SessionTable.onUpgrade(database, oldVer, newVer);
    }

	/**
	 * Add new Exam record into database
	 * @param exam Exam object to insert into the database. Should have a null ID field
	 * @return ID of the newly created Exam record
	 */
    public long createExam(Exam exam){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(ExamTable.TABLE_EXAMS, null, toValues(exam));
    }

	/**
	 * Add new Session record into database
	 * @param session Session object to insert into the database. Should have a null ID field
	 * @return ID of the newly created Session record
	 */
    public long createSession(Session session){
        SQLiteDatabase db = getWritableDatabase();
        return db.insert(SessionTable.TABLE_SESSIONS, null, toValues(session));
    }

	/**
	 * Utility method to convert a cursor to an Exam object
	 * @param curs
	 * @return Exam object represented by cursor position
	 */
	public Exam cursorToExam(Cursor curs){
		long exId = curs.getLong(curs.getColumnIndex(ExamTable.COL_ID));
		long exTimeRevised = curs.getLong(curs.getColumnIndex(ExamTable.COL_TIME_REVISED));
		String exTitle = curs.getString(curs.getColumnIndex(ExamTable.COL_TITLE));
		long exSub = curs.getLong(curs.getColumnIndex(ExamTable.COL_SUBJECT));
		long exDate = curs.getLong(curs.getColumnIndex(ExamTable.COL_DATE));

		return new Exam(exId, exTimeRevised, exDate, exSub, exTitle);
	}

	public Session cursorToSession(Cursor curs){
		long sID = curs.getLong(curs.getColumnIndex(SessionTable.COL_ID));
		long sDuration = curs.getLong(curs.getColumnIndex(SessionTable.COL_DURATION));
		long sTime = curs.getLong(curs.getColumnIndex(SessionTable.COL_TIME));
		long sExamID = curs.getLong(curs.getColumnIndex(SessionTable.COL_EXAM));
		long sSubID = curs.getLong(curs.getColumnIndex(SessionTable.COL_SUBJECT));


		return new Session(sID, sDuration, sTime, sExamID, sSubID);
	}

	/**
	 * Get single exam by ID
	 * @param id
	 * @return Exam object with given ID
	 */
    public Exam getExam(long id){
        SQLiteDatabase db = getReadableDatabase();

        String select = "SELECT * FROM " + ExamTable.TABLE_EXAMS + " WHERE "
                + ExamTable.COL_ID + " = " + id;
        Cursor curs = db.rawQuery(select, null);

        if (curs != null) {
            curs.moveToFirst();
			return cursorToExam(curs);
        }

        return null;
    }

	/**
	 * Get single session by ID
	 * @param id
	 * @return Session object with given ID
	 */
    public Session getSession(long id){
        SQLiteDatabase db = getReadableDatabase();

        String select = "SELECT * FROM " + ExamTable.TABLE_EXAMS + " WHERE "
                + ExamTable.COL_ID + " = " + id;
        Cursor curs = db.rawQuery(select, null);

        if (curs != null) {
            curs.moveToFirst();
			return cursorToSession(curs);
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
				exams.add(cursorToExam(curs));
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
				sessions.add(cursorToSession(curs));
			}while(curs.moveToNext());

			return sessions;
		}

		return null;
    }

	public List<Session> getSessionsAfter(Date date){
		SQLiteDatabase db = getReadableDatabase();
		List<Session> sessions = new ArrayList<Session>();

		String select = "SELECT * FROM " + SessionTable.TABLE_SESSIONS + " WHERE "
				+ SessionTable.COL_TIME + " > " + date.getTime()
				+ " ORDER BY " + SessionTable.COL_TIME;
		Cursor curs = db.rawQuery(select, null);

		if(curs.moveToFirst()){
			do{
				sessions.add(cursorToSession(curs));
			}while(curs.moveToNext());

			return sessions;
		}

		return null;
	}

	public int updateExam(Exam exam){
		SQLiteDatabase db = getWritableDatabase();

		ContentValues vals = toValues(exam);

		return db.update(
				ExamTable.TABLE_EXAMS,
				vals,
				ExamTable.COL_ID + " = ?",
				new String[]{String.valueOf(exam.getId())}
		);
	}

	public int updateSession(Session session){
		SQLiteDatabase db = getWritableDatabase();

		ContentValues vals = toValues(session);

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

	public ContentValues toValues(Session session){
		ContentValues vals = new ContentValues();
		if(session.hasID()){
			vals.put(SessionTable.COL_ID, session.getId());
		}
		vals.put(SessionTable.COL_EXAM, session.getExamID());
		vals.put(SessionTable.COL_DURATION, session.getDuration());
		vals.put(SessionTable.COL_TIME, session.getTime());
		vals.put(SessionTable.COL_SUBJECT, session.getSubjectID());

		return vals;
	}

	public ContentValues toValues(Exam exam){
		ContentValues vals = new ContentValues();
		if(exam.hasID()){
			vals.put(ExamTable.COL_ID, exam.getId());
		}
		vals.put(ExamTable.COL_DATE, exam.getDate());
		vals.put(ExamTable.COL_TIME_REVISED, exam.getTimeRevised());
		vals.put(ExamTable.COL_TITLE, exam.getTitle());
		vals.put(ExamTable.COL_SUBJECT, exam.getSubjectID());

		return vals;
	}

}

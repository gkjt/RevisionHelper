package gkjt.github.io.examrevisiontracker.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import gkjt.github.io.examrevisiontracker.datastructures.Session;

/**
 * Created by GTucker on 03/08/2016.
 */
public class SessionDataHelper extends RevisionDataHelper{

	public SessionDataHelper(Context context){
		super(context);
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

	public Session cursorToSession(Cursor curs){
		long sID = curs.getLong(curs.getColumnIndex(SessionTable.COL_ID));
		long sDuration = curs.getLong(curs.getColumnIndex(SessionTable.COL_DURATION));
		long sTime = curs.getLong(curs.getColumnIndex(SessionTable.COL_TIME));
		long sExamID = curs.getLong(curs.getColumnIndex(SessionTable.COL_EXAM));
		long sSubID = curs.getLong(curs.getColumnIndex(SessionTable.COL_SUBJECT));


		return new Session(sID, sDuration, sTime, sExamID, sSubID);
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

		return new ArrayList<Session>();
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


	public void deleteSession(long sessionId){
		SQLiteDatabase db = getWritableDatabase();

		db.delete(
				SessionTable.TABLE_SESSIONS,
				SessionTable.COL_ID  + " = ?",
				new String[]{String.valueOf(sessionId)}
		);
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
}

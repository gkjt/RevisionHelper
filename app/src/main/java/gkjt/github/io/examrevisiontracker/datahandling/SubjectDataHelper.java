package gkjt.github.io.examrevisiontracker.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.Subject;

/**
 * Created by GTucker on 03/08/2016.
 */
public class SubjectDataHelper extends RevisionDataHelper{
	public SubjectDataHelper(Context context){
		super(context);
	}

	public long createSubject(Subject subject){
		SQLiteDatabase db = getWritableDatabase();
		return db.insert(SessionTable.TABLE_SESSIONS, null, toValues(subject));
	}

	public List<Subject> getSubjects(){
		List<Subject> subs = new ArrayList<>();
		SQLiteDatabase db = getReadableDatabase();
		String select = "SELECT * FROM " + SubjectTable.TABLE_SUBJECTS;
		Cursor curs = db.rawQuery(select, null);
		if(curs.moveToFirst()){
			do{
				subs.add(cursorToSubject(curs));
			} while(curs.moveToNext());
			return subs;
		}
		return null;
	}

	public int updateSubject(Subject sub){
		SQLiteDatabase db = getWritableDatabase();
		return db.update(SubjectTable.TABLE_SUBJECTS,
				toValues(sub),
				SubjectTable.COL_ID + " = ?",
				new String[]{String.valueOf(sub.getId())});

	}

	public void deleteExam(long subId){
		SQLiteDatabase db = getWritableDatabase();

		db.delete(
				SubjectTable.TABLE_SUBJECTS,
				SubjectTable.COL_ID + " = ?",
				new String[]{String.valueOf(subId)}
		);
	}

	public Subject cursorToSubject(Cursor cursor){
		long id = cursor.getLong(cursor.getColumnIndex(SubjectTable.COL_ID));
		String title = cursor.getString(cursor.getColumnIndex(SubjectTable.COL_TITLE));
		return new Subject(id, title);
	}

	public ContentValues toValues(Subject subject){
		ContentValues vals = new ContentValues();
		if(subject.hasID()){
			vals.put(SubjectTable.COL_ID, subject.getId());
		}
		vals.put(SubjectTable.COL_TITLE, subject.getTitle());

		return vals;
	}
}

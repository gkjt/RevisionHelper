package gkjt.github.io.examrevisiontracker.datahandling;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.datastructures.Exam;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;

/**
 * Created by GTucker on 03/08/2016.
 */
public class ExamDataHelper extends RevisionDataHelper {
	public ExamDataHelper(Context context){
		super(context);
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
		short exPercent = curs.getShort(curs.getColumnIndex(ExamTable.COL_GRADE_PERCENTAGE));

		return new Exam(exId, exTimeRevised, exDate, exSub, exTitle, exPercent);
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

		return new ArrayList<Exam>();
	}

	public List<Exam> getExamsFromSubject(Subject constraint){
		List<Exam> exams = new ArrayList<Exam>();

		SQLiteDatabase db = getReadableDatabase();

		String select = "SELECT * FROM " + ExamTable.TABLE_EXAMS + " WHERE " + ExamTable.COL_SUBJECT + " = " + constraint.getId();
		Cursor curs = db.rawQuery(select, null);

		if(curs.moveToFirst()){
			long totalTime = 0;
			do{
				Exam e = cursorToExam(curs);
				totalTime += e.getTimeRevised();
				exams.add(e);
			}while(curs.moveToNext());

			for(Exam exam : exams){
				exam.setPercentageTime(((Long)(exam.getTimeRevised() / totalTime)).shortValue());
			}
			return exams;
		}

		return new ArrayList<Exam>();
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


	public void deleteExam(long examId){
		SQLiteDatabase db = getWritableDatabase();

		db.delete(
				ExamTable.TABLE_EXAMS,
				ExamTable.COL_ID + " = ?",
				new String[]{String.valueOf(examId)}
		);
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
		vals.put(ExamTable.COL_GRADE_PERCENTAGE, exam.getPercentageGrade());

		return vals;
	}

}

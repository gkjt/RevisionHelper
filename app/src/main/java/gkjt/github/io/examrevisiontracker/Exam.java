package gkjt.github.io.examrevisiontracker;

import android.content.ContentValues;

import gkjt.github.io.examrevisiontracker.datahandling.ExamTable;

/**
 * Created by GTucker on 31/08/2015.
 */
public class Exam {
    private long _id;
    private long _timeRevised;
    private long _date;
    private long _subjectID;
    private String _name;



    public Exam(){}

	public Exam(long _id, long _timeRevised, long _date, long _subjectID, String _name) {
		this._id = _id;
		this._timeRevised = _timeRevised;
		this._date = _date;
		this._subjectID = _subjectID;
		this._name = _name;
	}

	public void setId(long id){
        _id = id;
    }

    public void setTimeRevised(long time){
        _timeRevised = time;
    }

    public void setTitle(String title){
        _name = title;
    }

    public long getId(){
        return _id;
    }

    public  long getTimeRevised(){
        return _timeRevised;
    }

    public String getTitle(){
        return _name;
    }

    public long getDate() {
        return _date;
    }

    public void setDate(long _date) {
        this._date = _date;
    }

    public long getSubjectID() {
        return _subjectID;
    }

    public void setSubjectID(long _subjectID) {
        this._subjectID = _subjectID;
    }

    public boolean hasID(){
		return ((Long) _id) != null;
	}
}

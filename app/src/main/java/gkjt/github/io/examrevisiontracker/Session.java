package gkjt.github.io.examrevisiontracker;

import android.content.ContentValues;

import gkjt.github.io.examrevisiontracker.datahandling.SessionTable;

/**
 * Created by GTucker on 31/08/2015.
 */
public class Session {
    private long _id;
    private long _duration;
    private long _time;
    private long _examID;
    private long _subjectID;

    public Session(long _id, long _duration, long _time, long _examID, long _subjectID) {
        this._id = _id;
        this._duration = _duration;
        this._time = _time;
        this._examID = _examID;
        this._subjectID = _subjectID;
    }

    public Session(){}

	public Long getId(){
        if((Long) _id != null)
            return _id;
        return null;
    }

    public Long getTime(){
        return _time;
    }

    public Long getDuration(){
        return _duration;
    }

    public void setId(long id){
        _id = id;
    }

    public void setTime(long  time){
        _time = time;
    }

    public void setDuration(long duration){
        _duration = duration;
    }

    public long getExamID() {
        return _examID;
    }

    public void setExamID(long _examID) {
        this._examID = _examID;
    }

    public Long getSubjectID() {
        return _subjectID;
    }

    public void setSubjectID(long _subjectID) {
        this._subjectID = _subjectID;
    }

    public boolean hasID(){
        return ((Long) _id) != null;
    }
}

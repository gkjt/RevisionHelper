package gkjt.github.io.examrevisiontracker;

/**
 * Created by GTucker on 31/08/2015.
 */
public class Exam {
    private long _id, _timeRevised;
    private String _title;

    public Exam(){}

    public Exam(long id, long time, String title){
        this._id = id;
        this._timeRevised = time;
        this._title = title;
    }

    public void setId(long id){
        _id = id;
    }

    public void setTimeRevised(long time){
        _timeRevised = time;
    }

    public void setTitle(String title){
        _title = title;
    }

    public long getId(){
        return _id;
    }

    public  long getTimeRevised(){
        return _timeRevised;
    }

    public String getTitle(){
        return _title;
    }
}

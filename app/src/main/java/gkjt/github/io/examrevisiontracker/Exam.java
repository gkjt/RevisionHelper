package gkjt.github.io.examrevisiontracker;

/**
 * Created by GTucker on 31/08/2015.
 */
public class Exam {
    private long _id, _time;
    private String _title;

    //TODO: Sort out constructors

    public Exam(){}

    public void setId(long id){
        _id = id;
    }

    public void setTime(long time){
        _time = time;
    }

    public void setTitle(String title){
        _title = title;
    }

    public long getId(){
        return _id;
    }

    public  long getTime(){
        return _time;
    }

    public String getTitle(){
        return _title;
    }
}

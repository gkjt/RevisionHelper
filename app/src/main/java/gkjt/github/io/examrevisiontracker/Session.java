package gkjt.github.io.examrevisiontracker;

/**
 * Created by GTucker on 31/08/2015.
 */
public class Session {
    private long _id, _duration, _time;

    public Session(){}

	public long getId(){
        return _id;
    }

    public long getTime(){
        return _time;
    }

    public long getDuration(){
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
}

package gkjt.github.io.examrevisiontracker.datastructures;

import android.support.annotation.Nullable;

import java.io.Serializable;

/**
 * Created by User on 01/08/2016.
 */
public class Subject implements Serializable {
    long id;
    String title;
    int timeRevised;

    public Subject(String title) {
        this.title = title;
    }

    public Subject(long id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getTimeRevised() {
        return timeRevised;
    }

    public void setTimeRevised(int timeRevised) {
        this.timeRevised = timeRevised;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasID(){
        return ((Long) id) != null;
    }
}

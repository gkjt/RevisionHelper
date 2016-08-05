package gkjt.github.io.examrevisiontracker;

/**
 * Created by User on 01/08/2016.
 */
public class Subject {
    long id;
    String title;

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

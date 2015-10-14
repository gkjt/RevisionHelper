package gkjt.github.io.examrevisiontracker;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;

import java.util.Calendar;


public class RevisionTrackerBackgroundService extends Service {

    //Service that will do all the background tracking for the app.
        /*
        Provide reminders to revise
        Provide notifications of an ongoing or finished revision period when there is no activity
        Allow the app to go into the Revision UI instantly if an hour is running(?)

         */

    //That said, at the moment it will just handle counting down a
    // set amount of time, then notifying the user

    final String INTENT_DURATION_KEY = "duration";
    public enum intentCommands {
        START, STOP, PAUSE, RESUME
    };

    public enum states {
        IDLE, STARTED, PAUSED, FINISHED;
    }

    final int ONGOING_NOTIFICATION_ID = 1;

    states state;
    //duration, time remaining in milliseconds
    long duration;
    long timeRemaining;
    Calendar timeStarted;

    public RevisionTrackerBackgroundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate(){
        state = states.IDLE;
        timeRemaining = 0;
    }

    @Override
    public void onDestroy(){
        stopForeground(true);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        //handleCommand(intent);
        return START_STICKY;
    }

    private void handleIntent(Intent intent){
        Bundle intentData = intent.getExtras();
        timeRemaining = intentData.getLong(INTENT_DURATION_KEY);
        start();
    }

    private states getState() {
        return state;
    }

    private void start(){
        state = states.STARTED;
        timeStarted = Calendar.getInstance();
        duration = timeRemaining;
        //TODO: Make notifications for Foreground Notification
        //startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    private void pause(){
        state = states.PAUSED;
        updateRemainingTime();
    }

    private void updateRemainingTime() {
        Calendar timeElapsed = Calendar.getInstance();
        timeElapsed.setTimeInMillis(
                Calendar.getInstance().getTimeInMillis() - timeStarted.getTimeInMillis()
        );
        timeRemaining = duration - timeElapsed.getTimeInMillis();
    }

    private void finish(){
        state = states.FINISHED;
    }

    private Handler handler = new Handler();

    private Runnable updateTimer = new Runnable(){
        @Override
        public void run(){
            updateRemainingTime();

            if(timeRemaining <= 0){
                finish();
            }

            if(timeRemaining * 0.1 > 1000 && state == states.STARTED)
                handler.postDelayed(this, (long)(timeRemaining * 0.1));
            else if(state == states.STARTED)
                handler.postDelayed(this, 1000l);
        }

    };
}

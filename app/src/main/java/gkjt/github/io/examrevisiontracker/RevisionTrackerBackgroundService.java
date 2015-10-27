package gkjt.github.io.examrevisiontracker;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Handler;

public class RevisionTrackerBackgroundService extends Service {

    private enum State{
        STARTED, PAUSED, FINISHED
    }

    private State state;

    public class ServiceBinder extends Binder{
        RevisionTrackerBackgroundService getService(){
            return RevisionTrackerBackgroundService.this;
        }
    }

    @Override
    public void onCreate(){
        state = State.STARTED;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId){
        return START_NOT_STICKY;
    }

    private final IBinder binder = new ServiceBinder();

    @Override
    public IBinder onBind(Intent intent){
        return binder;
    }

    @Override
    public void onDestroy(){

    }
}

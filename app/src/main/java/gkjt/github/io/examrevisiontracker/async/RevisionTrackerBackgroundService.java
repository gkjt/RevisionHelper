package gkjt.github.io.examrevisiontracker.async;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.CountDownTimer;
import android.os.IBinder;

public class RevisionTrackerBackgroundService extends Service {
    public long timeRemaining;
    public CountDownTimer timer;
    public static final String DURATION_KEY = "duration";

    private State state;
    private TimerHandler finishHandler;

    public enum State{
        RUNNING, PAUSED, IDLE
    }

    public class ServiceBinder extends Binder{
        public RevisionTrackerBackgroundService getService(){
            return RevisionTrackerBackgroundService.this;
        }
    }

    public interface TimerHandler{
        void onTimerFinish();
        void onTimerTick(long timeRemaining);
    }

    @Override
    public void onCreate(){
        setState(State.IDLE);
    }

    public void setFinishHandler(TimerHandler t){
        finishHandler = t;
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
        if(timer != null)
            timer.cancel();
        super.onDestroy();
    }

    public State getState(){
        return state;
    }

    public void setState(State s){
        state = s;
    }

    public long getTimeRemaining(){
        return timeRemaining;
    }

    public void startTimer(long duration){
        if(duration > 0){
            setState(State.RUNNING);
            timer = new CountDownTimer(duration, 1000) {
                public void onTick(long millisRemaining){
                    timeRemaining = millisRemaining;
                    finishHandler.onTimerTick(millisRemaining);
                }

                public void onFinish(){
                    timeRemaining = 0l;
                    finishTimer();
                }
            };

            timer.start();
        }
    }

    public void pauseTimer(){
        setState(State.PAUSED);
        timer.cancel();
    }

    public void resumeTimer(){
        setState(State.RUNNING);
        startTimer(timeRemaining);
    }

    public void stopTimer(){
        timer.cancel();
        timeRemaining = 0;
        finishTimer();
    }

    public void finishTimer(){
        finishHandler.onTimerFinish();
        stopSelf();
    }
}

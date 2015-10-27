package gkjt.github.io.examrevisiontracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class RevisionTimerScreenActivity extends AppCompatActivity implements RevisionTrackerBackgroundService.TimerHandler{

    boolean isBound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_timer_screen);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_revision_timer_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
    }



    private RevisionTrackerBackgroundService bgService;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            bgService = ((RevisionTrackerBackgroundService.ServiceBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bgService = null;
        }
    };

    private void doBindService(){
        isBound = bindService(new Intent(RevisionTimerScreenActivity.this, RevisionTrackerBackgroundService.class),
                connection,
                0);
    }

    private void doUnbindService(){
        if(isBound){
            unbindService(connection);
            isBound = false;
        }
    }

    private void timerPause(){
        if(isBound){
            bgService.pauseTimer();
        }

        //TODO: Change to pause screen
    }

    private void timerResume(){
        if(isBound) {
            bgService.resumeTimer();
        }

        //TODO: Change back to resume
        //TODO: Vary opacity on timer?
    }

    @Override
    public void onTimerFinish(){
        //TODO: Transition to timer finished screen
    }

    @Override
    public void onTimerTick(long timeRemaining){
        //TODO: Update timer with number of minutes remaining
    }

    private void timerStop(){
        if(isBound){
            bgService.finishTimer();
        }

        //TODO: Return to main activity or a timer stopped activity
    }

    private void startTimer(long duration){
        startService(new Intent(this, RevisionTrackerBackgroundService.class)
                .putExtra(RevisionTrackerBackgroundService.DURATION_KEY, duration));

        doBindService();

        //TODO: Transition to timer screen
    }
}

package gkjt.github.io.examrevisiontracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;


public class RevisionTimerScreenActivity extends AppCompatActivity implements RevisionTrackerBackgroundService.TimerHandler, View.OnClickListener {

    boolean isBound = false;
    long duration = 60*60*1000;
    private RevisionTrackerBackgroundService bgService;
    FloatingActionButton startPauseFAB, stopFAB;
    TextView timeRemainingView;
    Animation fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revision_timer_screen);
        startPauseFAB = (FloatingActionButton) findViewById(R.id.start_pause_btn);
        stopFAB = (FloatingActionButton) findViewById(R.id.stop_btn);
        timeRemainingView = (TextView) findViewById(R.id.revision_timer_countdown);
        startService(new Intent(this, RevisionTrackerBackgroundService.class));
        doBindService();
        startPauseFAB.setOnClickListener(this);
        stopFAB.setOnClickListener(this);
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
        doUnbindService();
        super.onPause();
    }

    @Override
    public void onResume(){
        super.onResume();
        doBindService();
    }

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            isBound = true;
            bgService = ((RevisionTrackerBackgroundService.ServiceBinder) service).getService();
            startPauseFAB.setEnabled(true);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBound = false;
            bgService = null;
            startPauseFAB.setEnabled(false);
            startPauseFAB.setEnabled(false);
        }
    };

    private void doBindService(){
        bindService(
                new Intent(
                        RevisionTimerScreenActivity.this,
                        RevisionTrackerBackgroundService.class
                ),
                connection,
                Context.BIND_AUTO_CREATE);
    }

    private void doUnbindService(){
        unbindService(connection);
    }

    private void timerStart(){
        bgService.startTimer(duration);
        bgService.setFinishHandler(this);

        fade = new AlphaAnimation(1.0f, 0.4f);
        fade.setRepeatCount(Animation.INFINITE);
        fade.setRepeatMode(Animation.REVERSE);
        fade.setDuration(2000l);
        fade.start();
        timeRemainingView.setAnimation(fade);

        stopFAB.setEnabled(true);
        startPauseFAB.setImageDrawable(getDrawable(R.drawable.ic_pause));
    }

    private void timerPause(){
        bgService.pauseTimer();
        startPauseFAB.setImageDrawable(getDrawable(R.drawable.ic_play_arrow));
        fade.setRepeatCount(0);
    }

    private void timerResume(){
        bgService.resumeTimer();
        startPauseFAB.setImageDrawable(getDrawable(R.drawable.ic_pause));
        fade.reset();
        fade.setRepeatCount(Animation.INFINITE);
        fade.start();
    }

    @Override
    public void onTimerFinish(){
        //TODO: Transition to timer finished screen
        startPauseFAB.setImageDrawable(getDrawable(R.drawable.ic_play_arrow));
        finish();
    }

    @Override
    public void onTimerTick(long timeRemaining){
        int minutes = (int) (timeRemaining / (60*1000));
        TextView revisionTimer = (TextView)findViewById(R.id.revision_timer_countdown);
        revisionTimer.setText(new Integer(minutes).toString());
    }

    private void timerStop(){
        if(isBound){
            //// TODO: 24/11/2015  Show confirm dialog
            bgService.finishTimer();
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.start_pause_btn && isBound) {
            if (bgService.getState() == RevisionTrackerBackgroundService.State.IDLE) {
                timerStart();
            } else if (bgService.getState() == RevisionTrackerBackgroundService.State.RUNNING) {
                timerPause();
            } else if (bgService.getState() == RevisionTrackerBackgroundService.State.PAUSED) {
                timerResume();
            }
        } else if(v.getId() == R.id.stop_btn && isBound) {
            if (bgService.getState() != RevisionTrackerBackgroundService.State.IDLE) {
                timerStop();
            }
        } else {
            Log.e("RevisionTracker", "Button clicked with no service bind");
        }
    }
}

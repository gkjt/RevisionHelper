package gkjt.github.io.examrevisiontracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.async.AsyncChartInit;
import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datastructures.Exam;

public class MainActivity extends AppCompatActivity implements MenuItem.OnMenuItemClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
        }
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        PieChart hoursPerSubjectChart = (PieChart) findViewById(R.id.hoursPerSubject);
        LineChart hoursPerDayChart = (LineChart) findViewById(R.id.hoursPerDay);
        hoursPerSubjectChart.setNoDataTextDescription("You haven't revised yet");
        hoursPerDayChart.setNoDataTextDescription("You haven't revised yet");
        hoursPerSubjectChart.setUsePercentValues(true);
        hoursPerSubjectChart.animateX(500, Easing.EasingOption.EaseInCirc);
        new AsyncChartInit(this, hoursPerDayChart, hoursPerSubjectChart).execute();


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem examManage = menu.findItem(R.id.action_exam_manage);
        examManage.setOnMenuItemClickListener(this);
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
    public boolean onMenuItemClick(MenuItem item) {
        startActivity(new Intent(this, SubjectListActivity.class));
        return false;
    }

}

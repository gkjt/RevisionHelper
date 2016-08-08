package gkjt.github.io.examrevisiontracker;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.RevisionDataHelper;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //PieChart hoursPerSubjectChart = (PieChart) findViewById(R.id.hoursPerSubject);


        LineChart hoursPerDayChart = (LineChart) findViewById(R.id.hoursPerDay);
        ArrayList<Entry> valsDay = new ArrayList<Entry>();
        valsDay.add(new Entry(2, 0));
        valsDay.add(new Entry(7, 1));
        valsDay.add(new Entry(1, 2));
        valsDay.add(new Entry(9, 3));
        valsDay.add(new Entry(2, 4));
        valsDay.add(new Entry(6, 5));
        valsDay.add(new Entry(5, 6));
        LineDataSet setDay = new LineDataSet(valsDay, "Hours Revised Per Day");
        setDay.setAxisDependency(YAxis.AxisDependency.LEFT);

        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("M"); xVals.add("T"); xVals.add("W"); xVals.add("T");
        xVals.add("F"); xVals.add("S"); xVals.add("S");
        LineData dataDays = new LineData(xVals, setDay);
        hoursPerDayChart.setData(dataDays);

        hoursPerDayChart.setDescription("Hours Revised per Day");
        hoursPerDayChart.notifyDataSetChanged();
        hoursPerDayChart.invalidate();

        //startActivity(new Intent(this, RevisionTimerScreenActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void initCharts(){
        ExamDataHelper examDataSource = new ExamDataHelper(this);
        //Init pie chart
        PieChart hoursPerSubjectChart = (PieChart) findViewById(R.id.hoursPerSubject);
        List<Exam> exams = examDataSource.getExams();
        ArrayList<Entry> hoursEntries = new ArrayList<Entry>();
        ArrayList<String> hoursNames = new ArrayList<String>();
        int i = 0;
        for(Exam e : exams){
            hoursEntries.add(new Entry(e.getTimeRevised(), i++));
            hoursNames.add(e.getTitle());
        }
        PieDataSet hoursSet = new PieDataSet(hoursEntries, "Hours revised Per Subject");
        PieData hoursData = new PieData(hoursNames, hoursSet);
        hoursPerSubjectChart.setData(hoursData);
        hoursPerSubjectChart.notifyDataSetChanged();
        hoursPerSubjectChart.invalidate();
        //init line chart
    }

}

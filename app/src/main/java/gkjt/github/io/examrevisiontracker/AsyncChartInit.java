package gkjt.github.io.examrevisiontracker;

import android.content.Context;
import android.os.AsyncTask;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.RevisionDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.SessionDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.SessionTable;
import gkjt.github.io.examrevisiontracker.datahandling.SubjectDataHelper;

/**
 * Created by GTucker on 28/06/2016.
 */
public class AsyncChartInit extends AsyncTask<Void, Void, Void> {
	Context context;
	LineChart daysChart;
	PieChart subsChart;
	LineData daysData;
	PieData subsData;


	AsyncChartInit(Context context, LineChart lineChart, PieChart pieChart){
		this.context = context;
		this.daysChart = lineChart;
		this.subsChart = pieChart;
	}

	@Override
	protected void onPostExecute(Void v){
		daysChart.setData(daysData);
		daysChart.notifyDataSetChanged();
		subsChart.setData(subsData);
		subsChart.notifyDataSetChanged();
	}

	@Override
	protected Void doInBackground(Void ... a){
		initHoursPerDayLine();
		return null;
	}

	private void initHoursPerDayLine(){
		ExamDataHelper examHelper = new ExamDataHelper(context);
		SessionDataHelper sessionHelper = new SessionDataHelper(context);
		List<Exam> exams = examHelper.getExams();
		Calendar lastWeek = new GregorianCalendar();
		lastWeek.set(Calendar.HOUR_OF_DAY, 0);
		lastWeek.set(Calendar.MINUTE, 0);
		lastWeek.add(Calendar.DAY_OF_YEAR, -7);
		List<Session> sessionsLastWeek = sessionHelper.getSessionsAfter(lastWeek.getTime());

		int[] hoursPerDay = new int[7];
		String[] daysLabels = new String[7];
		int i=0;
		//TODO: Check ordering
		for(Session session : sessionsLastWeek){
			if(session.getTime() > lastWeek.getTimeInMillis()){
				i++;
				daysLabels[i] = getDayLabel(lastWeek.getTime());
				lastWeek.add(Calendar.DAY_OF_YEAR,1);
			}
			//TODO: Change session duration to be stored in minutes
			hoursPerDay[i] += session.getDuration() / (60*60*1000);

		}

		ArrayList<Entry> valsDay = new ArrayList<Entry>();
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -7);
		for (i=0; i < hoursPerDay.length; i++) {
			valsDay.add(new Entry(hoursPerDay[i], i));
		}

		LineDataSet daysDataSet = new LineDataSet(valsDay, "Hours completed over the last 7 days");
		daysDataSet.setAxisDependency(YAxis.AxisDependency.LEFT);
		LineData daysData = new LineData(daysLabels, daysDataSet);


	}

	private void initHoursPerSubjectPie(){
		HashMap<Subject, Integer> hoursPerSubject = new HashMap<>();
		SubjectDataHelper subHelper = new SubjectDataHelper(context);
		List<Entry> pieVals = new ArrayList<>();
		int i = 0;
		ArrayList<String> labels = new ArrayList<>();
		for(Subject sub : subHelper.getSubjects()){
			pieVals.add(new Entry(sub.getTimeRevised(), i));
		}



	}

	private String getDayLabel(Date date){
		//TODO: Test this output
		Locale locale = Locale.getDefault();
		DateFormat weekdayNameFormat = new SimpleDateFormat("EEEE", locale);
		return weekdayNameFormat.format(date);
	}

}

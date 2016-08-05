package gkjt.github.io.examrevisiontracker;

import android.content.Context;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.Entry;

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

import javax.security.auth.Subject;

import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.RevisionDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.SessionDataHelper;
import gkjt.github.io.examrevisiontracker.datahandling.SessionTable;

/**
 * Created by GTucker on 28/06/2016.
 */
public class AsyncChartInit extends AsyncTask<Void, Void, Void> {
	Context context;

	AsyncChartInit(Context context){
		this.context = context;
	}

	@Override
	protected Void doInBackground(Void ... a){
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


		HashMap<Subject, Integer> hoursPerSubject = new HashMap<>();
		//helper.getSubjects
		//for()

		return null;
	}

	private String getDayLabel(Date date){
		//TODO: Test this output
		Locale locale = Locale.getDefault();
		DateFormat weekdayNameFormat = new SimpleDateFormat("EEEE", locale);
		return weekdayNameFormat.format(date);
	}

}

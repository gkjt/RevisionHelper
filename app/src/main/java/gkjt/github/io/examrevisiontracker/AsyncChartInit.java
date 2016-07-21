package gkjt.github.io.examrevisiontracker;

import android.content.Context;
import android.os.AsyncTask;

import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import gkjt.github.io.examrevisiontracker.datahandling.RevisionDataHelper;

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
		RevisionDataHelper helper = new RevisionDataHelper(context);
		List<Exam> exams = helper.getExams();
		Calendar lastWeek = new GregorianCalendar();
		lastWeek.set(Calendar.HOUR_OF_DAY, 0);
		lastWeek.add(Calendar.DAY_OF_YEAR, -7);
		List<Session> sessionsLastWeek = helper.getSessionsAfter(lastWeek.getTime());

		int[] hoursPerDay = new int[7];
		int i=0;
		for(Session session : sessionsLastWeek){
			if(session.getTime() > lastWeek.getTimeInMillis()){
				i++;
				lastWeek.add(Calendar.DAY_OF_YEAR,1);
			}
			//TODO: Change session duration to be stored in minutes
			hoursPerDay[i] += session.getDuration() / (60*60*1000);

		}

		ArrayList<Entry> valsDay = new ArrayList<Entry>();
		//for()

		return null;
	}

}

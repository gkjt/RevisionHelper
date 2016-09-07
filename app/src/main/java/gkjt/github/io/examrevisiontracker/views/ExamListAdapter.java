package gkjt.github.io.examrevisiontracker.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import gkjt.github.io.examrevisiontracker.R;
import gkjt.github.io.examrevisiontracker.datastructures.Exam;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;

import java.util.List;

public class ExamListAdapter extends ArrayAdapter<Exam> {

	LayoutInflater inflater;
	List<Exam> exams;

	public ExamListAdapter(Context context, List<Exam> exams){
		super(context, R.layout.fragment_exam_list_item, exams);
		this.exams = exams;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup parent){
		View viewToReturn = null;
		ExamHolder holder;
		Exam exam = exams.get(pos);


		if(convertView != null){
			viewToReturn = convertView;
			holder = (ExamHolder) convertView.getTag();
		} else {
			viewToReturn = inflater.inflate(R.layout.fragment_exam_list_item, null);
			holder = new ExamHolder();
			holder.title = (TextView) viewToReturn.findViewById(R.id.exam_title);
			holder.percentageGrade = (TextView) viewToReturn.findViewById(R.id.exam_percentage_grade);
			holder.percentageTime = (TextView) viewToReturn.findViewById(R.id.exam_percentage_time);
			viewToReturn.setTag(holder);
		}

		holder.title.setText(exam.getTitle());
		holder.percentageGrade.setText(String.format(getContext().getString(R.string.exam_list_grade_percent), exam.getPercentageGrade()));
		holder.percentageTime.setText(String.format(getContext().getString(R.string.exam_list_time_percent), exam.getPercentageTime()));
		return viewToReturn;
	}

	public class ExamHolder {
		TextView title, percentageGrade, percentageTime, timeRevised;
	}
}

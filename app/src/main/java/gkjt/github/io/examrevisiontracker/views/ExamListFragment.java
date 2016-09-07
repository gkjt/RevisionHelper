package gkjt.github.io.examrevisiontracker.views;

import android.app.ListFragment;
import android.os.Bundle;

import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datastructures.Exam;

import java.util.ArrayList;
import java.util.List;

public class ExamListFragment extends ListFragment {
	public ExamListFragment(){
		super();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		ExamDataHelper helper = new ExamDataHelper(getActivity());
		ExamListAdapter adapter = new ExamListAdapter(getActivity(), new ArrayList<Exam>());
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
		//setOnItemClickListener
	}

	public void setData(List<Exam> exams){
		ExamListAdapter adapter = new ExamListAdapter(getActivity(), exams);
		setListAdapter(adapter);
		adapter.notifyDataSetChanged();
	}
}

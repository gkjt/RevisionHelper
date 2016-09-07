package gkjt.github.io.examrevisiontracker;

import android.app.ListFragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datastructures.Exam;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;
import gkjt.github.io.examrevisiontracker.views.ExamListAdapter;
import gkjt.github.io.examrevisiontracker.views.ExamListFragment;
import gkjt.github.io.examrevisiontracker.views.SubjectListAdapter;
import gkjt.github.io.examrevisiontracker.views.SubjectListFragment;

public class SubjectListActivity extends AppCompatActivity implements SubjectListFragment.SubjectSelectedListener {
	public static final String EXAM_BUNDLE_NAME = "gkjt.github.io.EXTRA_EXAM_LIST_BUNDLE";
	public static final String EXAM_BUNDLE_LIST_NAME = "gkjt.github.io.EXTRA_EXAM_LIST";
	SubjectListFragment subList;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
		}
		setContentView(R.layout.activity_subject_list);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				//TODO: Make this open subject/exam add dialog

			}
		});

	}

	@Override
	public void onStart(){
		super.onStart();

		subList = (SubjectListFragment) getFragmentManager().findFragmentById(R.id.subject_fragment);
		subList.setSubjectSelectedListener(this);
	}

	@Override
	public void onSubjectSelected(Subject selected) {
		ExamListFragment examfrag = (ExamListFragment) getFragmentManager().findFragmentById(R.id.exam_fragment);

		ExamDataHelper helper = new ExamDataHelper(this);
		ArrayList exams = (ArrayList)helper.getExamsFromSubject(selected);
		exams.add(new Exam(1l,60l,10l,20l,"Exam 1",(short)70));
		if(exams.isEmpty()) {
			//TODO: Make this open exam add dialog for selected subject
			Snackbar.make(findViewById(R.id.subject_activity_layout), "No exams for this subject - Tap here to add one!", Snackbar.LENGTH_LONG)
					.setAction("Action", null).show();
		}

		if(examfrag != null){
			examfrag.setData(exams);
		} else {
			Intent intent = new Intent(this, ExamListActivity.class);
			Bundle b = new Bundle();
			b.putSerializable(EXAM_BUNDLE_LIST_NAME, exams);
			intent.putExtra(EXAM_BUNDLE_NAME, b);
			startActivity(intent);
		}
	}
}

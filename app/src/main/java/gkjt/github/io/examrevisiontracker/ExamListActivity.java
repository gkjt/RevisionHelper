package gkjt.github.io.examrevisiontracker;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.datastructures.Exam;
import gkjt.github.io.examrevisiontracker.views.ExamListAdapter;
import gkjt.github.io.examrevisiontracker.views.ExamListFragment;
import gkjt.github.io.examrevisiontracker.views.SubjectListFragment;

public class ExamListActivity extends AppCompatActivity {
	List<Exam> exams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(getResources().getColor(R.color.primary_dark));
		}
		setContentView(R.layout.activity_exam_list);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
						.setAction("Action", null).show();
			}
		});

		Bundle b = getIntent().getExtras();
		exams = (ArrayList<Exam>)b.getBundle(SubjectListActivity.EXAM_BUNDLE_NAME).getSerializable(SubjectListActivity.EXAM_BUNDLE_LIST_NAME);

	}

	public void onStart(){
		super.onStart();

		ExamListFragment examList = (ExamListFragment) getFragmentManager().findFragmentById(R.id.exam_fragment);
		examList.setData(exams);
		//Log.d("examrevisionhelper", "Examlist empty " + (exams.isEmpty()));
	}

}

package gkjt.github.io.examrevisiontracker;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;

import java.util.ArrayList;

import gkjt.github.io.examrevisiontracker.datahandling.ExamDataHelper;
import gkjt.github.io.examrevisiontracker.datastructures.Exam;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;
import gkjt.github.io.examrevisiontracker.views.ExamListFragment;
import gkjt.github.io.examrevisiontracker.views.SubjectDialogFragment;
import gkjt.github.io.examrevisiontracker.views.SubjectListFragment;

public class SubjectListActivity extends AppCompatActivity implements SubjectListFragment.SubjectSelectedListener {
	public static final String EXAM_BUNDLE_NAME = "gkjt.github.io.EXTRA_EXAM_LIST_BUNDLE";
	public static final String EXAM_BUNDLE_LIST_NAME = "gkjt.github.io.EXTRA_EXAM_LIST";
	protected static final String SUBJECT_DIALOG_TAG = "SubjectDialog";
	SubjectListFragment subList;
	FloatingActionButton addButton;
	boolean fabMenuOpen = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.primary_dark));
		}
		setContentView(R.layout.activity_subject_list);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		addButton = (FloatingActionButton) findViewById(R.id.add_fab);
		addButton.setOnClickListener(new AddButtonListener());

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
		ArrayList<Exam> exams = (ArrayList<Exam>)helper.getExamsFromSubject(selected);
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

	private void showButtonMenu(){
		FloatingActionButton addSubjectButton = (FloatingActionButton) findViewById(R.id.add_subject_fab);
		CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) addSubjectButton.getLayoutParams();
		layoutParams.rightMargin += addButton.getWidth() * 0.43;
		layoutParams.bottomMargin += addButton.getHeight() * 1.25;
		addSubjectButton.setLayoutParams(layoutParams);
		addSubjectButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_subject_add_show));
		addSubjectButton.setVisibility(View.VISIBLE);
		addSubjectButton.setClickable(true);
		
		FloatingActionButton addExamButton = (FloatingActionButton) findViewById(R.id.add_exam_fab);
		layoutParams = (CoordinatorLayout.LayoutParams) addExamButton.getLayoutParams();
		layoutParams.rightMargin += addButton.getWidth() * 1.3;
		layoutParams.bottomMargin += addButton.getHeight() * 0.25;
		addExamButton.setLayoutParams(layoutParams);
		addExamButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_exam_add_show));
		addExamButton.setVisibility(View.VISIBLE);
		addExamButton.setClickable(true);
	}

	private void hideButtonMenu(){
		FloatingActionButton addSubjectButton = (FloatingActionButton) findViewById(R.id.add_subject_fab);
		CoordinatorLayout.LayoutParams layoutParams = (CoordinatorLayout.LayoutParams) addSubjectButton.getLayoutParams();
		layoutParams.rightMargin -= addButton.getWidth() * 0.43;
		layoutParams.bottomMargin -= addButton.getHeight() * 1.25;
		addSubjectButton.setLayoutParams(layoutParams);
		addSubjectButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_subject_add_hide));
		addSubjectButton.setVisibility(View.INVISIBLE);
		addSubjectButton.setClickable(false);

		FloatingActionButton addExamButton = (FloatingActionButton) findViewById(R.id.add_exam_fab);
		layoutParams = (CoordinatorLayout.LayoutParams) addExamButton.getLayoutParams();
		layoutParams.rightMargin -= addButton.getWidth() * 1.3;
		layoutParams.bottomMargin -= addButton.getHeight() * 0.25;
		addExamButton.setLayoutParams(layoutParams);
		addExamButton.startAnimation(AnimationUtils.loadAnimation(this, R.anim.anim_exam_add_hide));
		addExamButton.setVisibility(View.INVISIBLE);
		addExamButton.setClickable(false);
	}

	public class AddButtonListener implements FloatingActionButton.OnClickListener {

		@Override
		public void onClick(View v) {
			ExamListFragment examfrag = (ExamListFragment) getFragmentManager().findFragmentById(R.id.exam_fragment);
			if(examfrag != null) {
				if (!fabMenuOpen) {
					showButtonMenu();
					fabMenuOpen = true;
				} else {
					hideButtonMenu();
					fabMenuOpen = false;
				}
			}
			else {
				showNewSubjectDialog();
			}
		}

		private void showNewSubjectDialog(){
			SubjectDialogFragment.newInstance().show(getFragmentManager(), SUBJECT_DIALOG_TAG);
		}
	}


}

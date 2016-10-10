package gkjt.github.io.examrevisiontracker.views;

import android.app.ListFragment;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import gkjt.github.io.examrevisiontracker.datahandling.SubjectDataHelper;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;

/**
 * Created by GTucker on 18/08/2016.
 */
public class SubjectListFragment extends ListFragment {

	private SubjectSelectedListener listener;

	public SubjectListFragment(){
		super();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		SubjectDataHelper helper = new SubjectDataHelper(getActivity());
		SubjectListAdapter adapter = new SubjectListAdapter(getActivity(), helper.getSubjects());
		getListView().setOnLongClickListener();
		setListAdapter(adapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		if(listener != null){
			listener.onSubjectSelected(((SubjectListAdapter)getListAdapter()).getItem(position));
		}

	}

	public void setSubjectSelectedListener(SubjectSelectedListener s){
		listener = s;
	}

	public interface SubjectSelectedListener {
		void onSubjectSelected(Subject subject);
	}
}

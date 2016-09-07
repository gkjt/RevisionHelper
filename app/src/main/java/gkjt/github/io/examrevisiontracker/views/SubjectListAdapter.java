package gkjt.github.io.examrevisiontracker.views;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import gkjt.github.io.examrevisiontracker.R;
import gkjt.github.io.examrevisiontracker.datahandling.SubjectTable;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;

/**
 * Created by GTucker on 18/08/2016.
 */
public class SubjectListAdapter extends ArrayAdapter {

	private LayoutInflater inflater;
	private List<Subject> data;

	public SubjectListAdapter(Context context, List<Subject> subjectList){
		super(context, R.layout.fragment_subject_list_item, subjectList);
		this.data = subjectList;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent){
		SubjectHolder holder = new SubjectHolder();
		Subject s = data.get(position);

		View viewToPopulate;

		//Setup view holder pattern
		if(convertView == null){
			viewToPopulate = inflater.inflate(R.layout.fragment_subject_list_item, null);
			holder = new SubjectHolder();
			holder.title = (TextView) viewToPopulate.findViewById(R.id.subject_title);
			viewToPopulate.setTag(holder);
		} else {
			viewToPopulate = convertView;
			holder = (SubjectHolder) convertView.getTag();
		}

		//Initialise view
		holder.title.setText(s.getTitle());
		return viewToPopulate;
	}

	@Override
	public Subject getItem(int position){
		return data.get(position);
	}

	private class SubjectHolder {
		TextView title;
	}

}

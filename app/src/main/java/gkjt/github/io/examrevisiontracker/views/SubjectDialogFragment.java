package gkjt.github.io.examrevisiontracker.views;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import gkjt.github.io.examrevisiontracker.R;
import gkjt.github.io.examrevisiontracker.datahandling.SubjectDataHelper;
import gkjt.github.io.examrevisiontracker.datastructures.Subject;


/**
 * Created by User on 07/10/2016.
 */
public class SubjectDialogFragment extends DialogFragment {

    public static SubjectDialogFragment newInstance(){
        Bundle args = new Bundle();
        args.putBoolean("isNewSubject", true);
        SubjectDialogFragment sdf = new SubjectDialogFragment();
        sdf.setArguments(args);
        return sdf;
    }

    /**
     * Instantiate a new dialog for an existing subject
     * @param subject Subject to update
     * @return
     */
    public static SubjectDialogFragment newInstance(Subject subject){
        Bundle args = new Bundle();
        args.putBoolean("isNewSubject", false);
        args.putSerializable("Subject", subject);
        SubjectDialogFragment sdf = new SubjectDialogFragment();
        sdf.setArguments(args);
        return sdf;
    }

    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Bundle args = getArguments();
        final boolean isNewSubject = args.getBoolean("isNewSubject");
        final Subject subject = (Subject) args.getSerializable("Subject");

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        dialogBuilder.setView(R.layout.dialog_subject_edit);

        final EditText subjectTitleField = (EditText) getActivity().findViewById(R.id.subject_dialog_title_field);
        dialogBuilder
                .setTitle(R.string.subject_dialog_title)
                .setPositiveButton(R.string.dialog_confirm,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        SubjectDataHelper helper = new SubjectDataHelper(getActivity());

                        if(isNewSubject){
                            Subject subject = new Subject(String.valueOf(subjectTitleField.getText()));
                            helper.createSubject(subject);
                        } else {
                            subject.setTitle(String.valueOf(subjectTitleField.getText()));
                            helper.updateSubject(subject);
                        }

                        ((SubjectDialogFragment) dialog).getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.dialog_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ((SubjectDialogFragment) dialog).getDialog().dismiss();
                    }
                });

        if(!isNewSubject){
            subjectTitleField.setText(subject.getTitle());
        }

        return dialogBuilder.create();
    }

}

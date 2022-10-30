package edu.skku.cs.dokkang;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

class Subject {
    public String lecture_name;
    public String prefessor_name;
    public int lecture_id;
    public int lecture_no;
    public boolean checked;

    public Subject(String lecture_name, String prefessor_name, int lecture_id, int lecture_no, boolean checked) {
        this.lecture_name = lecture_name;
        this.prefessor_name = prefessor_name;
        this.lecture_id = lecture_id;
        this.lecture_no = lecture_no;
        this.checked = checked;
    }
}


public class EditSubjectListViewAdapter extends BaseAdapter {

    private ArrayList<Subject> item;
    private Context mContext;


    public EditSubjectListViewAdapter(ArrayList<Subject> item, Context mContext) {
        this.item = item;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int i) {
        return item.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.edit_subject_list, viewGroup, false);
        }

        TextView lecture_name = view.findViewById(R.id.subjectName_text);
        TextView lecture_no = view.findViewById(R.id.LectureNum_text);
        TextView professor = view.findViewById(R.id.ProfessorName_text);
        CheckBox checkBox = view.findViewById(R.id.checkBox);

        /*show lecture info */
        lecture_name.setText(item.get(i).lecture_name);
        lecture_no.setText(Integer.toString(item.get(i).lecture_no));
        professor.setText(item.get(i).prefessor_name);

        /*if checked before, then check the box*/
        checkBox.setChecked(item.get(i).checked);

        /*check lectures */
        checkBox.setOnClickListener(v -> {
            item.get(i).checked = checkBox.isChecked();
        });


        return view;
    }
}

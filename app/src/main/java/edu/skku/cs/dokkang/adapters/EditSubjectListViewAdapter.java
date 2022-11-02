package edu.skku.cs.dokkang.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.data_models.MySubject;

public class EditSubjectListViewAdapter extends BaseAdapter {

    private final List<MySubject> item;
    private final Context mContext;


    public EditSubjectListViewAdapter(List<MySubject> item, Context mContext) {
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
        lecture_name.setText(item.get(i).getName());
        lecture_no.setText(item.get(i).getNo());
        professor.setText(item.get(i).getProfessor());

        /*if checked before, then check the box*/
        checkBox.setChecked(item.get(i).getChecked());

        /*check lectures */
        checkBox.setOnClickListener(v -> {
            item.get(i).setChecked(checkBox.isChecked());
        });

        return view;
    }
}

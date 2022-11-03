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

    private List<MySubject> items;
    private final Context mContext;


    public EditSubjectListViewAdapter(List<MySubject> items, Context mContext) {
        this.items = items;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public MySubject getItem(int i) {
        return items.get(i);
    }

    public void setItems(List<MySubject> items) {
        this.items = items;
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

        MySubject item = getItem(i);

        /*show lecture info */
        lecture_name.setText(item.getName());
        lecture_no.setText(item.getNo());
        professor.setText(item.getProfessor());

        /*if checked before, then check the box*/
        checkBox.setChecked(item.getChecked());

        /*check lectures */
        checkBox.setOnClickListener(v -> {
            item.setChecked(checkBox.isChecked());
        });

        return view;
    }
}

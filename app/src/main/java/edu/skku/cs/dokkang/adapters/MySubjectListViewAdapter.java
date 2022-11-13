package edu.skku.cs.dokkang.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import edu.skku.cs.dokkang.R;
import edu.skku.cs.dokkang.activities.CommunityMain;
import edu.skku.cs.dokkang.activities.StudyGroupMain;
import edu.skku.cs.dokkang.data_models.MySubject;


public class MySubjectListViewAdapter extends BaseAdapter {

    private final List<MySubject> items;
    private final Context mContext;


    public MySubjectListViewAdapter(List<MySubject> items, Context mContext) {
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

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.subjectlist, viewGroup, false);
        }

        TextView lecture_name = view.findViewById(R.id.subjectNametextView);
        Button community_btn = view.findViewById(R.id.communitybutton);
        Button study_btn = view.findViewById(R.id.studybutton);

        MySubject item = getItem(i);

        /* set Textview as lecture name */
        lecture_name.setText(item.getName());

        /* go to the community activity*/
        community_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), CommunityMain.class);
            intent.putExtra("lecture", item.getName());
            intent.putExtra("professor", item.getProfessor());
            intent.putExtra("lecture_id", item.getId());
            intent.putExtra("lecture_no", item.getNo());
            viewGroup.getContext().startActivity(intent);
        });

        /* go to the study group activity*/
        study_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), StudyGroupMain.class);
            intent.putExtra("lecture", item.getName());
            intent.putExtra("professor", item.getProfessor());
            intent.putExtra("lecture_id", item.getId());
            intent.putExtra("lecture_no", item.getNo());
            viewGroup.getContext().startActivity(intent);
        });

        return view;
    }
}

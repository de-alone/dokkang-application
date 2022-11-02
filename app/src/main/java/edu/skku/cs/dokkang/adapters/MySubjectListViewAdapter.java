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

    private final List<MySubject> item;
    private final Context mContext;


    public MySubjectListViewAdapter(List<MySubject> item, Context mContext) {
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
            view = layoutInflater.inflate(R.layout.subjectlist, viewGroup, false);
        }

        TextView lecture_name = view.findViewById(R.id.subjectNametextView);
        Button community_btn = view.findViewById(R.id.communitybutton);
        Button study_btn = view.findViewById(R.id.studybutton);

        /* set Textview as lecture name */
        lecture_name.setText(item.get(i).getName());

        /* go to the community activity*/
        community_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), CommunityMain.class);
            intent.putExtra("lecture", item.get(i).getName());
            intent.putExtra("professor", item.get(i).getProfessor());
            intent.putExtra("lecture_id", item.get(i).getId());
            intent.putExtra("lecture_no", item.get(i).getNo());
            viewGroup.getContext().startActivity(intent);
        });

        /* go to the study group activity*/
        study_btn.setOnClickListener(v -> {
            Intent intent = new Intent(viewGroup.getContext(), StudyGroupMain.class);
            intent.putExtra("lecture", item.get(i).getName());
            intent.putExtra("professor", item.get(i).getProfessor());
            intent.putExtra("lecture_id", item.get(i).getId());
            intent.putExtra("lecture_no", item.get(i).getNo());
            viewGroup.getContext().startActivity(intent);
        });

        return view;
    }
}
